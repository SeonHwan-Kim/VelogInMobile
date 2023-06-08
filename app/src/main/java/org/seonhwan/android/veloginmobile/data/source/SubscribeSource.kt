package org.seonhwan.android.veloginmobile.data.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.seonhwan.android.veloginmobile.data.model.response.ResponsePostDto
import org.seonhwan.android.veloginmobile.data.model.response.ResponseSubscriberPostDto
import org.seonhwan.android.veloginmobile.data.service.SubscribeService
import javax.inject.Inject

class SubscribeSource @Inject constructor(
    private val subscribeService: SubscribeService,
) {
    suspend fun addSubscriber(name: String): Flow<Unit> = flow {
        emit(subscribeService.addSubscriber(name, "0"))
    }

    suspend fun deleteSubscriber(name: String): Flow<Unit> = flow {
        emit(subscribeService.deleteSubscriber(name))
    }

    suspend fun getSubscriberPost(): Flow<ResponseSubscriberPostDto<ResponsePostDto>> = flow {
        emit(subscribeService.getSubscriberPost())
    }
}
