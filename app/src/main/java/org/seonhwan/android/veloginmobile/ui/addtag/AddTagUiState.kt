package org.seonhwan.android.veloginmobile.ui.addtag

sealed class AddTagUiState {
    object Success : AddTagUiState()
    object Empty : AddTagUiState()
    data class Failure(val code: Int?) : AddTagUiState()
    object Error : AddTagUiState()
}
