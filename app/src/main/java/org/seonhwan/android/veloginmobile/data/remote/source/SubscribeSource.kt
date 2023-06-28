package org.seonhwan.android.veloginmobile.data.remote.source

import org.seonhwan.android.veloginmobile.data.remote.service.SubscribeService
import javax.inject.Inject

class SubscribeSource @Inject constructor(
    private val subscribeService: SubscribeService,
) {
    suspend fun addSubscriber(name: String) = subscribeService.addSubscriber(name, "0")

    suspend fun deleteSubscriber(name: String) = subscribeService.deleteSubscriber(name)

    suspend fun getSubscriberPost() = subscribeService.getSubscriberPost()

    suspend fun getSubscriber() = subscribeService.getSubscriber()

    suspend fun searchSubscriber(name: String) = subscribeService.searchSubscriber(name)
}
