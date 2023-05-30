package org.seonhwan.android.veloginmobile.ui.addtag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.domain.repository.TagRepository
import org.seonhwan.android.veloginmobile.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddTagViewModel @Inject constructor(
    private val tagRepository: TagRepository,
) : ViewModel() {
    private val _tagList = MutableLiveData<List<String>>()
    val tagList: LiveData<List<String>>
        get() = _tagList

    private val _tagListState = MutableLiveData<UiState>()
    val tagListState: LiveData<UiState>
        get() = _tagListState

    val _tagName = MutableLiveData("")
    private val tagName: String
        get() = _tagName.value?.trim() ?: ""

    private val _addTagState = MutableLiveData<UiState>()
    val addTagState: LiveData<UiState>
        get() = _addTagState

    init {
        getTag()
    }

    fun getTag() {
        viewModelScope.launch {
            tagRepository.GetTag().onSuccess { response ->
                _tagList.value = response
                Timber.tag("getTage").d(response.toString())
                _tagListState.value = UiState.Success
            }.onFailure { throwable ->
                Timber.tag("onFailure").e(throwable.toString())
                _tagListState.value = UiState.Failure(null)
            }
        }
    }

    fun addTag() {
        viewModelScope.launch {
            if (tagName != "") {
                tagRepository.PostAddTag(tag = tagName).onSuccess { response ->
                    Timber.tag("addTag Success").d(response.toString())
                    _addTagState.value = UiState.Success
                }.onFailure { throwable ->
                    Timber.tag("addTag failure").e(throwable)
                    _addTagState.value = UiState.Failure(null)
                }
            } else {
                _addTagState.value = UiState.Failure(null)
            }
        }
    }
}
