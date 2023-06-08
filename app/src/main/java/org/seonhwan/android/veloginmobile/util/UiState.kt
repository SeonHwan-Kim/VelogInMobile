package org.seonhwan.android.veloginmobile.util

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Failure(val code: Int?) : UiState<Nothing>()
}
