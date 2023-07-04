package org.seonhwan.android.veloginmobile.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.seonhwan.android.veloginmobile.domain.entity.SearchSubscriber

@Serializable
data class ResponseSearchSubscriberDto(
    @SerialName("profilePictureURL")
    val profilePictureUrl: String,
    @SerialName("profileURL")
    val profileUrl: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("validate")
    val validate: Boolean,
) {
    fun toSearchSubscriberEntity() = SearchSubscriber(
        profilePictureUrl = profilePictureUrl,
        profileUrl = profileUrl,
        userName = userName,
        validate = validate,
    )
}
