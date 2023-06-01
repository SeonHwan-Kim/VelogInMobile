package org.seonhwan.android.veloginmobile.data.service

import org.seonhwan.android.veloginmobile.data.model.response.ResponsePostDto
import org.seonhwan.android.veloginmobile.data.model.response.ResponseTagPostDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TagService {
    @GET("tag/gettag")
    suspend fun getTag(): List<String>

    @GET("tag/tagpost")
    suspend fun getTagPost(): ResponseTagPostDto<ResponsePostDto>

    @POST("tag/addtag")
    suspend fun postAddTag(
        @Query("tag") tag: String,
    ): Unit
}
