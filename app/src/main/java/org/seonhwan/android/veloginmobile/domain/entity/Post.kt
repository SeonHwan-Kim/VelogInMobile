package org.seonhwan.android.veloginmobile.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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
