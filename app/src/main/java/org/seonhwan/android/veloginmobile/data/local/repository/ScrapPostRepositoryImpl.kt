package org.seonhwan.android.veloginmobile.data.local.repository

import org.seonhwan.android.veloginmobile.VelogApplication
import org.seonhwan.android.veloginmobile.data.local.model.ScrapPost
import org.seonhwan.android.veloginmobile.domain.repository.local.ScrapPostRepository
import javax.inject.Inject

class ScrapPostRepositoryImpl @Inject constructor() : ScrapPostRepository {
    private val scrapPostDao = VelogApplication.appDatabaseInstance.scrapPostDao()
    override suspend fun addScrapPost(post: ScrapPost) =
        scrapPostDao.addScrapPost(post)

    override suspend fun getAllScrapPost() =
        scrapPostDao.getAllScrapPost()

    override suspend fun deleteScrapPost(url: String) =
        scrapPostDao.deleteScrapPost(url)
}
