package org.seonhwan.android.veloginmobile.ui.webview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.data.local.model.Folder
import org.seonhwan.android.veloginmobile.domain.repository.local.ScrapPostRepository
import org.seonhwan.android.veloginmobile.domain.repository.remote.SubscribeRepository
import org.seonhwan.android.veloginmobile.ui.addtag.AddTagViewModel.Companion.CODE_400
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Success
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val subscribeRepository: SubscribeRepository,
    private val scrapPostRepository: ScrapPostRepository,
) : ViewModel() {
    private val _addSubscriberState = MutableSharedFlow<UiState<Unit>>()
    val addSubscriberState: SharedFlow<UiState<Unit>>
        get() = _addSubscriberState

    private val _deleteSubscriberState = MutableSharedFlow<UiState<Unit>>()
    val deleteSubscriberState: SharedFlow<UiState<Unit>>
        get() = _deleteSubscriberState

    private val _isScrapPostState = MutableSharedFlow<Boolean>()
    val isScrapPostState: SharedFlow<Boolean>
        get() = _isScrapPostState

    val getScrapPostFolder = MutableLiveData<Folder>()

    fun isScrapPost(url: String) {
        viewModelScope.launch {
            scrapPostRepository.isScrapPost(url).collect { response ->
                if (response != null) {
                    _isScrapPostState.emit(true)
                } else {
                    _isScrapPostState.emit(false)
                }
            }
        }
    }

    fun addSubscriber(name: String) {
        viewModelScope.launch {
            subscribeRepository.addSubscriber(name).catch { error ->
                if (error is HttpException) {
                    when (error.code()) {
                        CODE_400 -> {
                            _addSubscriberState.emit(Failure(CODE_400))
                        }

                        else -> {
                            _addSubscriberState.emit(Failure(null))
                        }
                    }
                }
            }.collect { response ->
                _addSubscriberState.emit(Success(response))
            }
        }
    }

    fun deleteSubscriber(name: String) {
        viewModelScope.launch {
            subscribeRepository.deleteSubscriber(name).catch { error ->
                if (error is HttpException) {
                    when (error.code()) {
                        CODE_400 -> {
                            _deleteSubscriberState.emit(Failure(CODE_400))
                        }

                        else -> {
                            _deleteSubscriberState.emit(Failure(null))
                        }
                    }
                }
            }.collect { response ->
                _deleteSubscriberState.emit(Success(response))
            }
        }
    }
}
