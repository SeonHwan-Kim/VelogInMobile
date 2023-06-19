package org.seonhwan.android.veloginmobile.domain.repository.local

import kotlinx.coroutines.flow.Flow
import org.seonhwan.android.veloginmobile.data.local.model.Folder

interface FolderRepository {
    suspend fun addFolder(folder: Folder)

    suspend fun deleteFolder(folder: Folder)

    fun getAllFolder(): Flow<List<Folder>>

    fun getFolder(name: String): Flow<Folder>

    fun setFolderNumber(folder: Folder)
}
