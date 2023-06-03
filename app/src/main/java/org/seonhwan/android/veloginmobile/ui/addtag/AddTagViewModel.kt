package org.seonhwan.android.veloginmobile.ui.addtag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.domain.repository.TagRepository
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Error
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Success
import retrofit2.HttpException
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

    private val _deleteTagState = MutableLiveData<UiState>()
    val deleteTagState: LiveData<UiState>
        get() = _deleteTagState

    private val _popularTagList = MutableLiveData<List<String>>()
    val popularTagList: LiveData<List<String>>
        get() = _popularTagList

    private val _popularTagState = MutableLiveData<UiState>()
    val popularTagState: LiveData<UiState>
        get() = _popularTagState

    init {
        getTag()
        getPopularTag()
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

    fun addTag() {
        viewModelScope.launch {
            if (tagName != "") {
                tagRepository.postAddTag(tag = tagName).onSuccess { response ->
                    Timber.tag("addTag Success").d(response.toString())
                    _addTagState.value = Success
                }.onFailure { throwable ->
                    if (throwable is HttpException) {
                        when (throwable.code()) {
                            CODE_400 -> {
                                Timber.tag("addTag failure").e(throwable)
                                _addTagState.value = Failure(CODE_400)
                            }

                            else -> {
                                Timber.tag("addTag failure").e(throwable)
                                _addTagState.value = Error
                            }
                        }
                    }
                }
            } else {
                _addTagState.value = Failure(0)
            }
        }
    }

    fun deleteTag(tag: String) {
        viewModelScope.launch {
            tagRepository.deleteTag(tag).onSuccess {
                _deleteTagState.value = UiState.Success
            }.onFailure { throwable ->
                if (throwable is HttpException) {
                    when (throwable.code()) {
                        CODE_400 -> {
                            _deleteTagState.value = Failure(CODE_400)
                        }

                        else -> {
                            _deleteTagState.value = Error
                        }
                    }
                }
            }
        }
    }

    private fun getPopularTag() {
        viewModelScope.launch {
            tagRepository.getPopularTag()
                .onSuccess { response ->
                    _popularTagList.value = response
                    _popularTagState.value = Success
                }
                .onFailure { throwable ->
                    _popularTagState.value = Failure(null)
                }
        }
    }

    companion object {
        const val CODE_400 = 400
    }
}
