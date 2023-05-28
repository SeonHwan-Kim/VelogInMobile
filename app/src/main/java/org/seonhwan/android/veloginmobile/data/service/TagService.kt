package org.seonhwan.android.veloginmobile.data.service

import org.seonhwan.android.veloginmobile.data.model.response.ResponsePostDto
import org.seonhwan.android.veloginmobile.data.model.response.ResponseTagPostDto
import retrofit2.http.GET

interface TagService {
    @GET("tag/gettag")
    suspend fun getTag(): List<String>

    @GET("tag/tagpost")
    suspend fun getTagPost(): ResponseTagPostDto<ResponsePostDto>
}