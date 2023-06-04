package org.seonhwan.android.veloginmobile.data.repository

import org.seonhwan.android.veloginmobile.data.source.SubscribeSource
import org.seonhwan.android.veloginmobile.domain.repository.SubscribeRepository
import javax.inject.Inject

class SubscribeRepositoryImpl @Inject constructor(
    private val subscribeSource: SubscribeSource,
) : SubscribeRepository {
    override suspend fun addSubscriber(name: String): Result<Unit> =
        runCatching {
            subscribeSource.addSubscriber(name)
        }
}
