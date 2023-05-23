package org.seonhwan.android.veloginmobile.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.seonhwan.android.veloginmobile.data.ServicePool
import org.seonhwan.android.veloginmobile.data.model.response.ResponsePostDto
import org.seonhwan.android.veloginmobile.data.model.response.ResponseTagPostDto

class HomeViewModel : ViewModel() {
    private val _tagList = MutableLiveData<List<String>>()
    val tagList: LiveData<List<String>> get() = _tagList

    private val _tagPostList = MutableLiveData<List<ResponsePostDto>>()
    val tagPostList: LiveData<List<ResponsePostDto>> get() = _tagPostList

    fun getTag() {
        viewModelScope.launch {
            getTagList().onSuccess {
                _tagList.value = it
            }.onFailure {
                Log.d("failure", it.toString())
            }
        }
    }

    private suspend fun getTagList(): Result<List<String>> = kotlin.runCatching {
        ServicePool.getTagService.getTag()
    }

    fun getTagPost() {
        viewModelScope.launch {
            getTagPostList().onSuccess {
                _tagPostList.value = it.tagPostDtoList
                Log.d("success Tag Post List", it.tagPostDtoList.toString())
            }.onFailure {
                Log.d("failure", it.toString())
            }
        }
    }

    private suspend fun getTagPostList(): Result<ResponseTagPostDto<ResponsePostDto>> =
        kotlin.runCatching {
            ServicePool.getTagPostService.getTagPost()
        }
}
