package org.seonhwan.android.veloginmobile.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.seonhwan.android.veloginmobile.data.source.SubscribeSource
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.repository.SubscribeRepository
import javax.inject.Inject

class SubscribeRepositoryImpl @Inject constructor(
    private val subscribeSource: SubscribeSource,
) : SubscribeRepository {
    override suspend fun addSubscriber(name: String): Flow<Unit> = flow {
        subscribeSource.addSubscriber(name).collect {
            emit(it)
        }
    }

    override suspend fun deleteSubscriber(name: String): Flow<Unit> = flow {
        subscribeSource.deleteSubscriber(name).collect {
            emit(it)
        }
    }

    override suspend fun getSubscriberPost(): Flow<List<Post>> = flow {
        subscribeSource.getSubscriberPost().collect { response ->
            emit(response.subscribePostDtoList.map { data -> data.toPostEntity() })
        }
    }
}
