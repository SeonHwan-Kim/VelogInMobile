package org.seonhwan.android.veloginmobile.data.remote.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.seonhwan.android.veloginmobile.data.remote.source.SubscribeSource
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.repository.remote.SubscribeRepository
import javax.inject.Inject

class SubscribeRepositoryImpl @Inject constructor(
    private val subscribeSource: SubscribeSource,
) : SubscribeRepository {
    override suspend fun addSubscriber(name: String): Flow<Unit> = flow {
        emit(subscribeSource.addSubscriber(name))
    }

    override suspend fun deleteSubscriber(name: String): Flow<Unit> = flow {
        emit(subscribeSource.deleteSubscriber(name))
    }

    override suspend fun getSubscriberPost(): Flow<List<Post>> = flow {
        emit(subscribeSource.getSubscriberPost().subscribePostDtoList.map { data -> data.toPostEntity() })
    }
}
