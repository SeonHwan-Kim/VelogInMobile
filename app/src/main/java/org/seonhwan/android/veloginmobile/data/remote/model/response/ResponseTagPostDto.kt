package org.seonhwan.android.veloginmobile.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTagPostDto<T>(
    @SerialName("tagPostDtoList")
    val tagPostDtoList: List<T>,
)
