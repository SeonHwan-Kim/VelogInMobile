package org.seonhwan.android.veloginmobile.data.repository

import org.seonhwan.android.veloginmobile.data.source.TagSource
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.repository.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagSource: TagSource,
) : TagRepository {
    override suspend fun GetTag(): Result<List<String>> =
        runCatching {
            tagSource.getTagList()
        }

    override suspend fun GetTagPost(): Result<List<Post>> =
        runCatching {
            tagSource.getTagPostList().tagPostDtoList.map { data ->
                data.toPostEntity()
            }
        }
}
