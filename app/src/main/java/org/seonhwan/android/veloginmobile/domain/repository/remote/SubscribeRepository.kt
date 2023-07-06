package org.seonhwan.android.veloginmobile.domain.repository.remote

import kotlinx.coroutines.flow.Flow
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.entity.SearchSubscriber
import org.seonhwan.android.veloginmobile.domain.entity.Subscriber

interface SubscribeRepository {
    suspend fun addSubscriber(name: String): Flow<Unit>

    suspend fun deleteSubscriber(name: String): Flow<Unit>

    suspend fun getSubscriberPost(): Flow<List<Post>>

    suspend fun getSubscriber(): Flow<List<Subscriber>>

    suspend fun searchSubscriber(name: String): Flow<SearchSubscriber>

    suspend fun getTrendPost(): Flow<List<Post>>
}
