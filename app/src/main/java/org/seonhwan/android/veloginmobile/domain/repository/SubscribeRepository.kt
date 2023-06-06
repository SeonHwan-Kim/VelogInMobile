package org.seonhwan.android.veloginmobile.domain.repository

import org.seonhwan.android.veloginmobile.domain.entity.Post

interface SubscribeRepository {
    suspend fun addSubscriber(name: String): Result<Unit>

    suspend fun deleteSubscriber(name: String): Result<Unit>

    suspend fun getSubscriberPost(): Result<List<Post>>
}
