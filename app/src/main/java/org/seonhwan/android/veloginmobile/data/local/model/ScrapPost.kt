package org.seonhwan.android.veloginmobile.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.seonhwan.android.veloginmobile.domain.entity.Post

@Entity(tableName = "scrap_post")
data class ScrapPost(
    val folder: String?,
    val date: String,
    val img: String,
    val name: String,
    val isSubscribed: Boolean,
    val summary: String,
    val tag: List<String>,
    val title: String,
    @PrimaryKey
    val url: String,
    val record: Long
)

fun ScrapPost.toPost() = Post(
    date = date,
    img = img,
    name = name,
    isSubscribed = isSubscribed,
    summary = summary,
    tag = tag,
    title = title,
    url = url,
)
