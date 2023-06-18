package org.seonhwan.android.veloginmobile.data.remote.service

import org.seonhwan.android.veloginmobile.data.remote.model.response.ResponsePostDto
import org.seonhwan.android.veloginmobile.data.remote.model.response.ResponseSubscriberPostDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SubscribeService {
    @POST("subscribe/addsubscriber")
    suspend fun addSubscriber(
        @Query("name") name: String,
        @Query("fcmToken") fcmToken: String,
    ): Unit

    @DELETE("subscribe/unsubscribe/{targetName}")
    suspend fun deleteSubscriber(
        @Path("targetName") name: String,
    ): Unit

    @GET("subscribe/subscriberpost")
    suspend fun getSubscriberPost(): ResponseSubscriberPostDto<ResponsePostDto>
}
