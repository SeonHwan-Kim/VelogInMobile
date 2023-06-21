package org.seonhwan.android.veloginmobile.data.local.repository

import kotlinx.coroutines.flow.Flow
import org.seonhwan.android.veloginmobile.VelogApplication
import org.seonhwan.android.veloginmobile.data.local.model.Folder
import org.seonhwan.android.veloginmobile.domain.repository.local.FolderRepository
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor() : FolderRepository {
    private val folderDao = VelogApplication.appDatabaseInstance.folderDao()

    override suspend fun addFolder(folder: Folder) =
        folderDao.addFolder(folder)

    override suspend fun deleteFolder(folder: Folder) =
        folderDao.deleteFolder(folder)

    override fun getAllFolder(): Flow<List<Folder>> =
        folderDao.getAllFolder()

    override fun getFolder(name: String): Flow<Folder> =
        folderDao.getFolder(name)
}
