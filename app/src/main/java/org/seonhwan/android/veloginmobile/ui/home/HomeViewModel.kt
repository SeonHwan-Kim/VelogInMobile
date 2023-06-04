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
    val tagList: LiveData<List<String>>
        get() = _tagList

    private val _tagListState = MutableLiveData<UiState>()
    val tagListState: LiveData<UiState>
        get() = _tagListState

    private val _tagPostList = MutableLiveData<List<Post>>()
    val tagPostList: LiveData<List<Post>>
        get() = _tagPostList

    private val _tagPostListState = MutableLiveData<UiState>()
    val tagPostListState: LiveData<UiState>
        get() = _tagPostListState

    init {
        getTag()
    }

    fun getTag() {
        viewModelScope.launch {
            tagRepository.getTag().onSuccess { response ->
                _tagList.value = response
                Timber.tag("getTage").d(response.toString())
                _tagListState.value = UiState.Success
            }.onFailure { throwable ->
                Timber.tag("onFailure").e(throwable.toString())
                _tagListState.value = UiState.Failure(null)
            }
        }
    }

    fun getTagPost() {
        viewModelScope.launch {
            tagRepository.getTagPost().onSuccess { response ->
                _tagPostList.value = response
                Timber.tag("success Tag Post List").d(response.toString())
                _tagPostListState.value = UiState.Success
            }.onFailure { throwable ->
                Timber.tag("failure Tag Post List").e(throwable)
                _tagPostListState.value = UiState.Failure(null)
            }
        }
    }
}
