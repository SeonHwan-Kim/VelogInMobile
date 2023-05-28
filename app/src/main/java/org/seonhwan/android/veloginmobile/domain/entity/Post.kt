package org.seonhwan.android.veloginmobile.domain.entity

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
)
