package org.seonhwan.android.veloginmobile.domain.repository.local

import kotlinx.coroutines.flow.Flow
import org.seonhwan.android.veloginmobile.data.local.model.ScrapPost

interface ScrapPostRepository {
    suspend fun addScrapPost(post: ScrapPost)

    suspend fun getAllScrapPost(): Flow<List<ScrapPost>>

    suspend fun deleteScrapPost(url: String)

    suspend fun getFolderScrapPost(folderName: String): Flow<List<ScrapPost>>
}
