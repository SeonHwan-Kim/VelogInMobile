package org.seonhwan.android.veloginmobile.domain.repository

import org.seonhwan.android.veloginmobile.domain.entity.Post

interface TagRepository {
    suspend fun getTag(): Result<List<String>>

    suspend fun getTagPost(): Result<List<Post>>

    suspend fun postAddTag(tag: String): Result<Unit>

    suspend fun deleteTag(tag: String): Result<Unit>

    suspend fun getPopularTag(): Result<List<String>>
}
