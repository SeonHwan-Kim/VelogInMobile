package org.seonhwan.android.veloginmobile.domain.repository

import org.seonhwan.android.veloginmobile.domain.entity.Post

interface TagRepository {
    suspend fun GetTag(): Result<List<String>>

    suspend fun GetTagPost(): Result<List<Post>>

    suspend fun PostAddTag(tag: String): Result<Unit>

    suspend fun DeleteTag(tag: String): Result<Unit>
}
