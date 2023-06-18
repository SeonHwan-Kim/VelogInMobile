package org.seonhwan.android.veloginmobile.ui.scrap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.data.local.model.ScrapPost
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.entity.toScrapPost
import org.seonhwan.android.veloginmobile.domain.repository.local.ScrapPostRepository
import org.seonhwan.android.veloginmobile.util.UiState
import org.seonhwan.android.veloginmobile.util.UiState.Failure
import org.seonhwan.android.veloginmobile.util.UiState.Loading
import org.seonhwan.android.veloginmobile.util.UiState.Success
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val scrapPostRepository: ScrapPostRepository,
) : ViewModel() {
    private val _getAllScrapPostState = MutableSharedFlow<UiState<List<ScrapPost>>>()
    val getAllScrapPostState: SharedFlow<UiState<List<ScrapPost>>>
        get() = _getAllScrapPostState

    init {
        getAllScrapPost()
    }

    fun getAllScrapPost() {
        viewModelScope.launch {
            scrapPostRepository.getAllScrapPost()
                .onStart { _getAllScrapPostState.emit(Loading) }
                .catch { error ->
                    _getAllScrapPostState.emit(Failure(null))
                }
                .collect { response ->
                    _getAllScrapPostState.emit(Success(response))
                }
        }
    }

    fun scrapPost(post: Post, folder: String?) {
        viewModelScope.launch {
            scrapPostRepository.addScrapPost(post.toScrapPost(folder))
        }
    }

    fun deleteScrapPost(url: String) {
        viewModelScope.launch {
            scrapPostRepository.deleteScrapPost(url)
        }
    }
}
