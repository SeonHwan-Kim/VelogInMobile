package org.seonhwan.android.veloginmobile.data.source

import org.seonhwan.android.veloginmobile.data.service.SubscribeService
import javax.inject.Inject

class SubscribeSource @Inject constructor(
    private val subscribeService: SubscribeService,
) {
    suspend fun addSubscriber(name: String) = subscribeService.addSubscriber(name, "0")

    suspend fun deleteSubscriber(name: String) = subscribeService.deleteSubscriber(name)

    suspend fun getSubscriberPost() = subscribeService.getSubscriberPost()
}
