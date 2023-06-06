package org.seonhwan.android.veloginmobile.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.repository.SubscribeRepository
import org.seonhwan.android.veloginmobile.domain.repository.TagRepository
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Error
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Success
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tagRepository: TagRepository,
    private val subscribeRepository: SubscribeRepository,
) : ViewModel() {
    private val _tagList = MutableLiveData<List<String>>()
    val tagList: LiveData<List<String>>
        get() = _tagList

    private val _tagListState = MutableLiveData<UiState>()
    val tagListState: LiveData<UiState>
        get() = _tagListState

    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>>
        get() = _postList

    private val _postListState = MutableLiveData<UiState>()
    val postListState: LiveData<UiState>
        get() = _postListState

    init {
        getTag()
    }

    fun getTag() {
        viewModelScope.launch {
            tagRepository.getTag().onSuccess { response ->
                _tagList.value = response
                Timber.tag("getTage").d(response.toString())
                _tagListState.value = Success
            }.onFailure { throwable ->
                Timber.tag("onFailure").e(throwable.toString())
                _tagListState.value = Failure(null)
            }
        }
    }

    fun getTagPost() {
        viewModelScope.launch {
            tagRepository.getTagPost().onSuccess { response ->
                _postList.value = response
                _postListState.value = Success
            }.onFailure { throwable ->
                _postListState.value = Failure(null)
            }
        }
    }

    fun getSubscriberPost() {
        viewModelScope.launch {
            subscribeRepository.getSubscriberPost()
                .onSuccess { response ->
                    _postList.value = response
                    _postListState.value = Success
                }
                .onFailure { throwable ->
                    if (throwable is HttpException) {
                        when (throwable.code()) {
                            CODE_202 -> {
                                _postListState.value = Failure(CODE_202)
                            }

                            else -> {
                                _postListState.value = Error
                            }
                        }
                    }
                }
        }
    }

    companion object {
        const val CODE_202 = 202
    }
}
