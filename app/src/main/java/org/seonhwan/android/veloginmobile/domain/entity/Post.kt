package org.seonhwan.android.veloginmobile.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.seonhwan.android.veloginmobile.data.local.model.ScrapPost

@Parcelize
data class Post(
    val date: String,
    val img: String,
    val name: String,
    val isSubscribed: Boolean,
    val summary: String,
    val tag: List<String>,
    val title: String,
    val url: String,
) : Parcelable

fun Post.toScrapPost(folder: String?) = ScrapPost(
    folder = folder,
    date = date,
    img = img,
    name = name,
    isSubscribed = isSubscribed,
    summary = summary,
    tag = tag,
    title = title,
    url = url,
    record = System.currentTimeMillis()
)
