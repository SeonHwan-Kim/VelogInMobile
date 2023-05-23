package org.seonhwan.android.veloginmobile.data.service

import org.seonhwan.android.veloginmobile.data.model.response.ResponsePostDto
import org.seonhwan.android.veloginmobile.data.model.response.ResponseTagPostDto
import retrofit2.http.GET

interface GetTagPostService {
    @GET("tag/tagpost")
    suspend fun getTagPost(): ResponseTagPostDto<ResponsePostDto>
}
