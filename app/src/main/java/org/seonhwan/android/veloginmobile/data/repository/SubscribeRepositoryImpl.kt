package org.seonhwan.android.veloginmobile.data.repository

import org.seonhwan.android.veloginmobile.data.source.SubscribeSource
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.repository.SubscribeRepository
import javax.inject.Inject

class SubscribeRepositoryImpl @Inject constructor(
    private val subscribeSource: SubscribeSource,
) : SubscribeRepository {
    override suspend fun addSubscriber(name: String): Result<Unit> =
        runCatching {
            subscribeSource.addSubscriber(name)
        }

    override suspend fun deleteSubscriber(name: String): Result<Unit> =
        runCatching {
            subscribeSource.deleteSubscriber(name)
        }

    override suspend fun getSubscriberPost(): Result<List<Post>> =
        runCatching {
            subscribeSource.getSubscriberPost().subscribePostDtoList.map { data ->
                data.toPostEntity()
            }
        }
}
