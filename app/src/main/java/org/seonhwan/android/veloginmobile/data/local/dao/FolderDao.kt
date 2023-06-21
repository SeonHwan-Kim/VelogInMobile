package org.seonhwan.android.veloginmobile.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.seonhwan.android.veloginmobile.data.local.model.Folder

@Dao
interface FolderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFolder(folder: Folder)

    @Delete
    suspend fun deleteFolder(folder: Folder)

    @Query("SELECT * FROM folder ORDER BY name ASC")
    fun getAllFolder(): Flow<List<Folder>>

    @Query("SELECT * FROM folder WHERE name = :name")
    fun getFolder(name: String): Flow<Folder>

    @Update
    suspend fun updateFolder(folder: Folder)
}
