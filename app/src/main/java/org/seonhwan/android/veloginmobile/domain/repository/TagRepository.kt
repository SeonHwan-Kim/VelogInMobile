package org.seonhwan.android.veloginmobile.domain.repository

import kotlinx.coroutines.flow.Flow
import org.seonhwan.android.veloginmobile.domain.entity.Post

interface TagRepository {
    suspend fun getTag(): Flow<List<String>>

//    suspend fun getTagPost(): Result<List<Post>>
    suspend fun getTagPost(): Flow<List<Post>>

//    suspend fun postAddTag(tag: String): Result<Unit>
    suspend fun postAddTag(tag: String): Flow<Unit>

//    suspend fun deleteTag(tag: String): Result<Unit>
    suspend fun deleteTag(tag: String): Flow<Unit>

//    suspend fun getPopularTag(): Result<List<String>>
    suspend fun getPopularTag(): Flow<List<String>>
}
