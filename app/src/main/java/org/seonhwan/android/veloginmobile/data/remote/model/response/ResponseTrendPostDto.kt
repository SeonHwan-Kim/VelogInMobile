package org.seonhwan.android.veloginmobile.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTrendPostDto<T>(
    @SerialName("trendPostDtos")
    val trendPostDto: List<T>,
)