package org.seonhwan.android.veloginmobile.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.data.local.model.Folder
import org.seonhwan.android.veloginmobile.data.local.model.ScrapPost
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.entity.toScrapPost
import org.seonhwan.android.veloginmobile.domain.repository.local.FolderRepository
import org.seonhwan.android.veloginmobile.domain.repository.local.ScrapPostRepository
import org.seonhwan.android.veloginmobile.domain.repository.remote.SubscribeRepository
import org.seonhwan.android.veloginmobile.domain.repository.remote.TagRepository
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tagRepository: TagRepository,
    private val subscribeRepository: SubscribeRepository,
    private val scrapPostRepository: ScrapPostRepository,
    private val folderRepository: FolderRepository,
) : ViewModel() {
    private val _tagList = MutableSharedFlow<UiState<List<String>>>()
    val tagList: SharedFlow<UiState<List<String>>>
        get() = _tagList

    private val _postList = MutableSharedFlow<UiState<List<Post>>>()
    val postList: SharedFlow<UiState<List<Post>>>
        get() = _postList

    private val _getAllScrapPostState = MutableSharedFlow<UiState<List<ScrapPost>>>()
    val getAllScrapPostState: SharedFlow<UiState<List<ScrapPost>>>
        get() = _getAllScrapPostState

    private var getFolder: Folder? = null

    init {
        getTag()
        getAllScrapPost()
    }

    fun getTag() {
        viewModelScope.launch {
            tagRepository.getTag().catch { error ->
                when (error) {
                    is ConnectException -> {
                        _tagList.emit(Failure(NETWORK_ERR))
                    }

                    else -> {
                        _tagList.emit(Failure(null))
                    }
                }
            }.collect { response ->
                _tagList.emit(Success(response))
            }
        }
    }

    fun getTagPost() {
        viewModelScope.launch {
            tagRepository.getTagPost().onStart { _postList.emit(Loading) }.catch { error ->
                when (error) {
                    is ConnectException -> _postList.emit(Failure(NETWORK_ERR))

                    else -> _postList.emit(Failure(null))
                }
            }.collect { response ->
                _postList.emit(Success(response))
            }
        }
    }

    fun getSubscriberPost() {
        viewModelScope.launch {
            subscribeRepository.getSubscriberPost().onStart { _postList.emit(Loading) }
                .catch { throwable ->
                    if (throwable is HttpException) {
                        _postList.emit(Failure(throwable.code()))
                        Timber.d(throwable.message())
                    } else {
                        _postList.emit(Failure(null))
                        Timber.d(throwable)
                    }
                }.collect { response ->
                    _postList.emit(Success<List<Post>>(response))
                    Timber.d(response.toString())
                }
        }
    }

    private fun getAllScrapPost() {
        viewModelScope.launch {
            scrapPostRepository.getAllScrapPost().onStart { _getAllScrapPostState.emit(Loading) }
                .catch { error ->
                    if (error is HttpException) {
                        _getAllScrapPostState.emit(Failure(error.code()))
                    }
                }.collect { response ->
                    Timber.d(response.toString())
                    _getAllScrapPostState.emit(Success(response))
                }
        }
    }

    fun scrapPost(post: Post, folder: String?) {
        viewModelScope.launch {
            scrapPostRepository.addScrapPost(post.toScrapPost(folder))
        }
    }

    fun deleteScrapPost(post: Post, folderName: String?) {
        viewModelScope.launch {
            scrapPostRepository.deleteScrapPost(post.url)
            if (folderName != null) {
                folderRepository.getFolder(folderName).take(1).collect { folder ->
                    getFolder = folder
                    decreaseFolderNumber()
                    Timber.d(folder.toString())
                }
            }
        }
    }

    private fun decreaseFolderNumber() {
        viewModelScope.launch {
            getFolder?.let {
                val newFolder = it.copy(it.name, it.size - 1)
                folderRepository.updateFolder(newFolder)
            }
        }
    }

    companion object {
        const val CODE_202 = 202
        const val NETWORK_ERR = 0
    }
}
