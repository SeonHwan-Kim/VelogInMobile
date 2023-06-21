package org.seonhwan.android.veloginmobile.ui.scrap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.data.local.model.Folder
import org.seonhwan.android.veloginmobile.data.local.model.ScrapPost
import org.seonhwan.android.veloginmobile.domain.repository.local.FolderRepository
import org.seonhwan.android.veloginmobile.domain.repository.local.ScrapPostRepository
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Success
import javax.inject.Inject

@HiltViewModel
class ScrapBottomSheetViewModel @Inject constructor(
    private val scrapPostRepository: ScrapPostRepository,
    private val folderRepository: FolderRepository,
) : ViewModel() {
    val folderName = MutableLiveData("")

    private val _getAllFolderListState = MutableSharedFlow<UiState<List<Folder>>>()
    val getAllFolderList: SharedFlow<UiState<List<Folder>>>
        get() = _getAllFolderListState

    init {
        getAllFolderList()
    }

    private fun getAllFolderList() {
        viewModelScope.launch {
            folderRepository.getAllFolder().catch {
                _getAllFolderListState.emit(Failure(null))
            }.collect { response ->
                _getAllFolderListState.emit(Success(response))
            }
        }
    }

    fun addFolder() {
        viewModelScope.launch {
            folderRepository.addFolder(Folder(folderName.value.toString(), 0))
        }
    }

    fun addFolder(folder: Folder) {
        viewModelScope.launch {
            folderRepository.addFolder(folder)
        }
    }

    fun addScrapPost(post: ScrapPost) {
        viewModelScope.launch {
            scrapPostRepository.addScrapPost(post)
        }
    }
}
