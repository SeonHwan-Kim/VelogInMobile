package org.seonhwan.android.veloginmobile.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val comment: Int,
    val date: String,
    val img: String,
    val like: Int,
    val name: String,
    val summary: String,
    val tag: List<String>,
    val title: String,
    val url: String,
) : Parcelable
