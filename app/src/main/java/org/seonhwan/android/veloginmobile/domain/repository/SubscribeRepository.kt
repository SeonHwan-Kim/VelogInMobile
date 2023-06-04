package org.seonhwan.android.veloginmobile.domain.repository

interface SubscribeRepository {
    suspend fun addSubscriber(name: String): Result<Unit>
}
