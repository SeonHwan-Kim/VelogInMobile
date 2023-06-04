package org.seonhwan.android.veloginmobile.data.repository

import org.seonhwan.android.veloginmobile.data.source.TagSource
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.repository.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagSource: TagSource,
) : TagRepository {
    override suspend fun getTag(): Result<List<String>> =
        runCatching {
            tagSource.getTagList()
        }

    override suspend fun getTagPost(): Result<List<Post>> =
        runCatching {
            tagSource.getTagPostList().tagPostDtoList.map { data ->
                data.toPostEntity()
            }
        }

    override suspend fun postAddTag(tag: String): Result<Unit> =
        runCatching {
            tagSource.postAddTag(tag)
        }

    override suspend fun deleteTag(tag: String): Result<Unit> =
        runCatching {
            tagSource.deleteTag(tag)
        }

    override suspend fun getPopularTag(): Result<List<String>> =
        runCatching {
            tagSource.getPopularTag()
        }
}
