package org.seonhwan.android.veloginmobile.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSubscriberPostDto<T>(
    @SerialName("subscribePostDtoList")
    val subscribePostDtoList: List<T>,
)
