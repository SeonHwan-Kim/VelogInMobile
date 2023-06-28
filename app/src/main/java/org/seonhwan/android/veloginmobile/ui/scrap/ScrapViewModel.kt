package org.seonhwan.android.veloginmobile.ui.scrap

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
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val scrapPostRepository: ScrapPostRepository,
    private val folderRepository: FolderRepository,
) : ViewModel() {
    private val _getAllScrapPostState = MutableSharedFlow<UiState<List<ScrapPost>>>()
    val getAllScrapPostState: SharedFlow<UiState<List<ScrapPost>>>
        get() = _getAllScrapPostState

    private val _getFolderState = MutableSharedFlow<UiState<List<Folder>>>()
    val getFolderState: SharedFlow<UiState<List<Folder>>>
        get() = _getFolderState

    private var getFolder: Folder? = null

    fun getFolder() {
        viewModelScope.launch {
            folderRepository.getAllFolder().catch { error ->
                _getFolderState.emit(Failure(null))
            }.collect { response ->
                _getFolderState.emit(Success(response))
            }
        }
    }

    fun getAllScrapPost() {
        viewModelScope.launch {
            scrapPostRepository.getAllScrapPost().onStart { _getAllScrapPostState.emit(Loading) }
                .catch {
                    _getAllScrapPostState.emit(Failure(null))
                }.collect { response ->
                    _getAllScrapPostState.emit(Success(response))
                }
        }
    }

    fun getFolderScrapPost(folderName: String) {
        viewModelScope.launch {
            scrapPostRepository.getFolderScrapPost(folderName)
                .onStart { _getAllScrapPostState.emit(Loading) }.catch {
                    _getAllScrapPostState.emit(Failure(null))
                }.collect { response ->
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

    fun addFolder(folderName: String) {
        viewModelScope.launch {
            folderRepository.addFolder(Folder(folderName, 0))
        }
    }
}
