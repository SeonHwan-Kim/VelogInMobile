package org.seonhwan.android.veloginmobile.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.seonhwan.android.veloginmobile.domain.entity.Subscriber

@Serializable
data class ResponseSubscriberDto(
    @SerialName("img")
    val img: String,
    @SerialName("name")
    val name: String,
) {
    fun toSubscriberEntity() = Subscriber(
        img = img,
        name = name,
    )
}
