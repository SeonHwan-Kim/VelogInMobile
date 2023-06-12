package org.seonhwan.android.veloginmobile.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.seonhwan.android.veloginmobile.data.local.model.ScrapPost

@Dao
interface ScrapPostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScrapPost(post: ScrapPost)

    @Query("SELECT * FROM scrap_post")
    fun getAllScrapPost(): Flow<List<ScrapPost>>

    @Delete
    suspend fun deleteScrapPost(scrapPost: ScrapPost)
}
