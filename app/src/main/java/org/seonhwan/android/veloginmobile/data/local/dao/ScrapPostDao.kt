package org.seonhwan.android.veloginmobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.seonhwan.android.veloginmobile.data.local.model.ScrapPost

@Dao
interface ScrapPostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScrapPost(post: ScrapPost)

    @Query("SELECT * FROM scrap_post ORDER BY record DESC")
    fun getAllScrapPost(): Flow<List<ScrapPost>>

    @Query("SELECT * FROM scrap_post WHERE folder = :folder ORDER BY record DESC")
    fun getFolderScrapPost(folder: String): Flow<List<ScrapPost>>

    @Query("DELETE FROM scrap_post WHERE url = :url")
    suspend fun deleteScrapPost(url: String)
}
