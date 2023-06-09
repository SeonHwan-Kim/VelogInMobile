package org.seonhwan.android.veloginmobile.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.domain.entity.Post
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
) : ViewModel() {
    private val _tagList = MutableSharedFlow<UiState<List<String>>>()
    val tagList: SharedFlow<UiState<List<String>>>
        get() = _tagList

    private val _postList = MutableSharedFlow<UiState<List<Post>>>()
    val postList: SharedFlow<UiState<List<Post>>>
        get() = _postList

    init {
        getTag()
    }

    fun getTag() {
        viewModelScope.launch {
            tagRepository.getTag()
                .catch { error ->
                    when (error) {
                        is ConnectException -> {
                            _tagList.emit(Failure(NETWORK_ERR))
                        }

                        else -> {
                            _tagList.emit(Failure(null))
                        }
                    }
                }
                .collect { response ->
                    _tagList.emit(Success(response))
                }
        }
    }

    fun getTagPost() {
        viewModelScope.launch {
            tagRepository.getTagPost()
                .onStart { _postList.emit(Loading) }
                .catch { error ->
                    when (error) {
                        is ConnectException -> _postList.emit(Failure(NETWORK_ERR))

                        else -> _postList.emit(Failure(null))
                    }
                }
                .collect { response ->
                    _postList.emit(Success(response))
                }
        }
    }

    fun getSubscriberPost() {
        viewModelScope.launch {
            subscribeRepository.getSubscriberPost()
                .onStart { _postList.emit(Loading) }
                .catch { throwable ->
                    if (throwable is HttpException) {
                        _postList.emit(Failure(throwable.code()))
                        Timber.d(throwable.message())
                    } else {
                        _postList.emit(Failure(null))
                        Timber.d(throwable)
                    }
                }
                .collect { response ->
                    _postList.emit(Success<List<Post>>(response))
                    Timber.d(response.toString())
                }
        }
    }

    companion object {
        const val CODE_202 = 202
        const val NETWORK_ERR = 0
    }
}
