package org.seonhwan.android.veloginmobile.ui.webview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.domain.repository.SubscribeRepository
import org.seonhwan.android.veloginmobile.ui.addtag.AddTagViewModel.Companion.CODE_400
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Error
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Success
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val subscribeRepository: SubscribeRepository,
) : ViewModel() {
    private val _addSubscriberState = MutableLiveData<UiState>()
    val addSubscriberState: LiveData<UiState>
        get() = _addSubscriberState

    private val _deleteSubscriberState = MutableLiveData<UiState>()
    val deleteSubscriberState: LiveData<UiState>
        get() = _deleteSubscriberState

    fun addSubscriber(name: String) {
        viewModelScope.launch {
            subscribeRepository.addSubscriber(name)
                .onSuccess {
                    _addSubscriberState.value = Success
                }
                .onFailure { throwable ->
                    if (throwable is HttpException) {
                        when (throwable.code()) {
                            CODE_400 -> {
                                Failure(CODE_400)
                            }

                            else -> {
                                Error
                            }
                        }
                    }
                }
        }
    }

    fun deleteSubscriber(name: String) {
        viewModelScope.launch {
            subscribeRepository.deleteSubscriber(name)
                .onSuccess {
                    _deleteSubscriberState.value = Success
                }
                .onFailure { throwable ->
                    if (throwable is HttpException) {
                        when (throwable.code()) {
                            CODE_400 -> {
                                _deleteSubscriberState.value = Failure(CODE_400)
                            }

                            else -> {
                                _deleteSubscriberState.value = Error
                            }
                        }
                    }
                }
        }
    }
}
