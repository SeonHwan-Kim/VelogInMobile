package org.seonhwan.android.veloginmobile.data.service

import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface SubscribeService {
    @POST("subscribe/addsubscriber")
    suspend fun addSubscriber(
        @Query("name") name: String,
    ): Unit
}
