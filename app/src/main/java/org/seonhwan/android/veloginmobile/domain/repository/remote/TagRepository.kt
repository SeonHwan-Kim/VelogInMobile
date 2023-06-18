package org.seonhwan.android.veloginmobile.domain.repository.remote

import kotlinx.coroutines.flow.Flow
import org.seonhwan.android.veloginmobile.domain.entity.Post

interface TagRepository {
    suspend fun getTag(): Flow<List<String>>

    suspend fun getTagPost(): Flow<List<Post>>

    suspend fun postAddTag(tag: String): Flow<Unit>

    suspend fun deleteTag(tag: String): Flow<Unit>

    suspend fun getPopularTag(): Flow<List<String>>
}
