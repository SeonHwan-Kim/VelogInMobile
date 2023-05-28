package org.seonhwan.android.veloginmobile.domain.repository

import org.seonhwan.android.veloginmobile.domain.entity.Post

interface TagRepository {
    suspend fun GetTag(): Result<List<String>>

    suspend fun GetTagPost(): Result<List<Post>>
}
