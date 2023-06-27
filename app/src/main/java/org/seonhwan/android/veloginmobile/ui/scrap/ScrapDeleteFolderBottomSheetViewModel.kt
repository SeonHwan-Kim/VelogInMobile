package org.seonhwan.android.veloginmobile.ui.scrap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.domain.repository.local.FolderRepository
import org.seonhwan.android.veloginmobile.domain.repository.local.ScrapPostRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScrapDeleteFolderBottomSheetViewModel @Inject constructor(
    private val scrapPostRepository: ScrapPostRepository,
    private val folderRepository: FolderRepository,
) : ViewModel() {
    fun deleteScrapFolder(folderName: String) {
        viewModelScope.launch {
            scrapPostRepository.getFolderScrapPost(folderName)
                .collect { response ->
                    response.map { scrapPost ->
                        Timber.d(scrapPost.url)
                        scrapPostRepository.deleteScrapPost(scrapPost.url)
                    }
                }
        }
    }

    fun deleteFolder(folderName: String) {
        viewModelScope.launch {
            folderRepository.deleteFolder(folderName)
        }
    }
}
