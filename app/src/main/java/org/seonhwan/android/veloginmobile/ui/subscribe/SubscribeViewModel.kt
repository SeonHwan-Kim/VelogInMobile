package org.seonhwan.android.veloginmobile.ui.subscribe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.domain.entity.Subscriber
import org.seonhwan.android.veloginmobile.domain.repository.remote.SubscribeRepository
import org.seonhwan.android.veloginmobile.ui.addtag.AddTagViewModel.Companion.CODE_400
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SubscribeViewModel @Inject constructor(
    private val subscribeRepository: SubscribeRepository,
) : ViewModel() {
    private val _subscriberList = MutableSharedFlow<UiState<List<Subscriber>>>()
    val subscriberList: SharedFlow<UiState<List<Subscriber>>>
        get() = _subscriberList

    private val _deleteSubscriberState = MutableSharedFlow<UiState<Unit>>()
    val deleteSubscriberState: SharedFlow<UiState<Unit>>
        get() = _deleteSubscriberState

    private val _addSubscriberState = MutableSharedFlow<UiState<Unit>>()
    val addSubscriberState: SharedFlow<UiState<Unit>>
        get() = _addSubscriberState

    init {
        getSubscriber()
    }

    fun getSubscriber() {
        viewModelScope.launch {
            subscribeRepository.getSubscriber().onStart { _subscriberList.emit(Loading) }
                .catch { error ->
                    when (error) {
                        is HttpException -> {
                            _subscriberList.emit(Failure(error.code()))
                        }

                        else -> {
                            _subscriberList.emit(Failure(null))
                        }
                    }
                }.collect { response ->
                    _subscriberList.emit(Success(response))
                }
        }
    }

    fun unSubscribe(subscriberName: String) {
        viewModelScope.launch {
            Timber.d(subscriberName)
            subscribeRepository.deleteSubscriber(subscriberName).catch { error ->
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

    fun addSubscriber(userName: String) {
        viewModelScope.launch {
            subscribeRepository.addSubscriber(userName)
                .catch { _addSubscriberState.emit(Failure(null)) }.collect { response ->
                    _addSubscriberState.emit(Success(response))
                }
        }
    }
}
