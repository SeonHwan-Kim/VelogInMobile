package org.seonhwan.android.veloginmobile.ui.addtag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.domain.repository.TagRepository
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Success
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddTagViewModel @Inject constructor(
    private val tagRepository: TagRepository,
) : ViewModel() {
    private val _tagState = MutableSharedFlow<UiState<List<String>>>()
    val tagState: SharedFlow<UiState<List<String>>>
        get() = _tagState

    val _tagName = MutableLiveData("")
    private val tagName: String
        get() = _tagName.value?.trim() ?: ""

    private val _addTagState = MutableSharedFlow<UiState<Unit>>()
    val addTagState: SharedFlow<UiState<Unit>>
        get() = _addTagState

    private val _deleteTagState = MutableSharedFlow<UiState<Unit>>()
    val deleteTagState: SharedFlow<UiState<Unit>>
        get() = _deleteTagState

    private val _popularTagState = MutableSharedFlow<UiState<List<String>>>()
    val popularTagState: SharedFlow<UiState<List<String>>>
        get() = _popularTagState

    init {
        getTag()
        getPopularTag()
    }

    fun getTag() {
        viewModelScope.launch {
            tagRepository.getTag().catch { error ->
                _tagState.emit(Failure(null))
            }.collect { response ->
                _tagState.emit(Success(response))
            }
        }
    }

    fun addTag() {
        viewModelScope.launch {
            if (tagName != "") {
                tagRepository.postAddTag(tagName).catch { error ->
                    if (error is HttpException) {
                        when (error.code()) {
                            CODE_400 -> {
                                Timber.tag("addTag failure").e(error)
                                _addTagState.emit(Failure(CODE_400))
                            }

                            else -> {
                                Timber.tag("addTag failure").e(error)
                                _addTagState.emit(Failure(null))
                            }
                        }
                    }
                }.collect { response ->
                    _addTagState.emit(Success(response))
                }
            }
        }
    }

    fun deleteTag(tag: String) {
        viewModelScope.launch {
            tagRepository.deleteTag(tag).catch { error ->
                if (error is HttpException) {
                    when (error.code()) {
                        CODE_400 -> {
                            _deleteTagState.emit(Failure(CODE_400))
                        }

                        else -> {
                            _deleteTagState.emit(Failure(null))
                        }
                    }
                }
            }.collect { response ->
                _deleteTagState.emit(Success(response))
            }
        }
    }

    private fun getPopularTag() {
        viewModelScope.launch {
            tagRepository.getPopularTag().catch { error ->
                _popularTagState.emit(Failure(null))
            }
                .collect { response ->
                    _popularTagState.emit(Success(response))
                }
        }
    }

    companion object {
        const val CODE_400 = 400
    }
}
