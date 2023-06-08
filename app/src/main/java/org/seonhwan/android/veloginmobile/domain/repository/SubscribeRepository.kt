package org.seonhwan.android.veloginmobile.domain.repository

import kotlinx.coroutines.flow.Flow
import org.seonhwan.android.veloginmobile.domain.entity.Post

interface SubscribeRepository {
    suspend fun addSubscriber(name: String): Flow<Unit>

    suspend fun deleteSubscriber(name: String): Flow<Unit>

    suspend fun getSubscriberPost(): Flow<List<Post>>
}
