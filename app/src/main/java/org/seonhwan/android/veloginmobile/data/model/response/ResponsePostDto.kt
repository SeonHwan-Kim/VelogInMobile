package org.seonhwan.android.veloginmobile.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.seonhwan.android.veloginmobile.domain.entity.Post

@Serializable
data class ResponsePostDto(
    @SerialName("comment")
    val comment: Int,
    @SerialName("date")
    val date: String,
    @SerialName("img")
    val img: String,
    @SerialName("like")
    val like: Int,
    @SerialName("name")
    val name: String,
    @SerialName("subscribed")
    val subscribed: Boolean,
    @SerialName("summary")
    val summary: String,
    @SerialName("tag")
    val tag: List<String>,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String,
) {
    fun toPostEntity() = Post(
        date = date,
        img = img,
        name = name,
        isSubscribed = subscribed,
        summary = summary,
        tag = tag,
        title = title,
        url = url,
    )
}
