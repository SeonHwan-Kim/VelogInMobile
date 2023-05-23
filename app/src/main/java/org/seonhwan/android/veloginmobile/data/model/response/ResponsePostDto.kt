package org.seonhwan.android.veloginmobile.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    @SerialName("summary")
    val summary: String,
    @SerialName("tag")
    val tag: List<String>,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String,
)
