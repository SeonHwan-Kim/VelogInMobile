package org.seonhwan.android.veloginmobile.ui.subscribe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.domain.entity.SearchSubscriber
import org.seonhwan.android.veloginmobile.domain.repository.remote.SubscribeRepository
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val subscribeRepository: SubscribeRepository,
) : ViewModel() {
    val userName = MutableLiveData("")

    private val _searchUserState = MutableSharedFlow<UiState<SearchSubscriber>>()
    val searchUserState: SharedFlow<UiState<SearchSubscriber>>
        get() = _searchUserState

    val isUserValid = MutableLiveData(false)

    val isUserNameNotEmpty = userName.map { userName -> userName.isNotEmpty() }

    fun searchUser() {
        viewModelScope.launch {
            subscribeRepository.searchSubscriber(userName.value.toString())
                .onStart {
                    _searchUserState.emit(Loading)
                }
                .catch { error ->
                    when (error) {
                        is HttpException -> {
                            _searchUserState.emit(Failure(error.code()))
                        }

                        else -> {
                            _searchUserState.emit(Failure(null))
                        }
                    }
                }
                .collect { response ->
                    isUserValid.value = response.validate
                    _searchUserState.emit(Success(response))
                }
        }
    }

    fun addSubscriber(userName: String) {
        viewModelScope.launch {
            subscribeRepository.addSubscriber(userName)
                .collect()
        }
    }
}
