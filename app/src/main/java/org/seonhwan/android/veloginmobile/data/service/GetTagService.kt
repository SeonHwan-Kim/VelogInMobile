package org.seonhwan.android.veloginmobile.data.service

import retrofit2.http.GET

interface GetTagService {
    @GET("tag/gettag")
    suspend fun getTag(): List<String>
}
