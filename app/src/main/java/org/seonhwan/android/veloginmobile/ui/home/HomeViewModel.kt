package org.seonhwan.android.veloginmobile.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.repository.TagRepository
import org.seonhwan.android.veloginmobile.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tagRepository: TagRepository,
) : ViewModel() {
    private val _tagList = MutableLiveData<List<String>>()
    val tagList: LiveData<List<String>> get() = _tagList

    private val _tagListState = MutableLiveData<UiState>()
    val tagListState: LiveData<UiState>
        get() = _tagListState

    private val _tagPostList = MutableLiveData<List<Post>>()
    val tagPostList: LiveData<List<Post>> get() = _tagPostList

    private val _tagPostListState = MutableLiveData<UiState>()
    val tagPostListState: LiveData<UiState>
        get() = _tagPostListState

    fun getTag() {
        viewModelScope.launch {
            tagRepository.GetTag().onSuccess { response ->
                _tagListState.value = UiState.Success
                _tagList.value = response
            }.onFailure { throwable ->
                _tagListState.value = UiState.Failure(null)
                Timber.tag("onFailure").e(throwable.toString())
            }
        }
    }

    fun getTagPost() {
        viewModelScope.launch {
            tagRepository.GetTagPost().onSuccess { response ->
                _tagPostListState.value = UiState.Success
                _tagPostList.value = response
                Timber.tag("success Tag Post List").d(response.toString())
            }.onFailure { throwable ->
                _tagPostListState.value = UiState.Failure(null)
                Timber.tag("failure Tag Post List").e(throwable)
            }
        }
    }
}
