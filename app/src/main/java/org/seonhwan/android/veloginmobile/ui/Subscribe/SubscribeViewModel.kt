package org.seonhwan.android.veloginmobile.ui.Subscribe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.domain.entity.SearchSubscriber
import org.seonhwan.android.veloginmobile.domain.entity.Subscriber
import org.seonhwan.android.veloginmobile.domain.repository.remote.SubscribeRepository
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SubscribeViewModel @Inject constructor(
    private val subscribeRepository: SubscribeRepository,
) : ViewModel() {
    val subscriberName = MutableLiveData("")

    private val _subscriberList = MutableSharedFlow<UiState<List<Subscriber>>>()
    val subscriberList: SharedFlow<UiState<List<Subscriber>>>
        get() = _subscriberList

    private val _searchSubscriber = MutableSharedFlow<UiState<SearchSubscriber>>()
    val searchSubscriber: SharedFlow<UiState<SearchSubscriber>>
        get() = _searchSubscriber

    private val _addSubscriber = MutableSharedFlow<UiState<Unit>>()
    val addSubscriber: SharedFlow<UiState<Unit>>
        get() = _addSubscriber

    init {
        getSubscriber()
    }

    private fun getSubscriber() {
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

    fun searchSubscriber() {
        viewModelScope.launch {
            subscribeRepository.searchSubscriber(subscriberName.value.toString()).catch { error ->
                when (error) {
                    is HttpException -> {
                        _searchSubscriber.emit(Failure(error.code()))
                    }

                    else -> {
                        _searchSubscriber.emit(Failure(null))
                    }
                }
            }.collect { response ->
                _searchSubscriber.emit(Success(response))
            }
        }
    }

    fun addSubscriber() {
        viewModelScope.launch {
            subscribeRepository.addSubscriber(subscriberName.value.toString()).catch { error ->
                when (error) {
                    is HttpException -> {
                        _addSubscriber.emit(Failure(error.code()))
                    }

                    else -> {
                        _addSubscriber.emit(Failure(null))
                    }
                }
            }.collect { response ->
                _addSubscriber.emit(Success(response))
            }
        }
    }
}