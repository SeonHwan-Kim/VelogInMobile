package org.seonhwan.android.veloginmobile.domain.repository

interface SubscribeRepository {
    suspend fun addSubscriber(name: String): Result<Unit>

    suspend fun deleteSubscriber(name: String): Result<Unit>
}
