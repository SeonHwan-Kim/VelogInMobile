package org.seonhwan.android.veloginmobile.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.seonhwan.android.veloginmobile.data.source.TagSource
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.repository.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagSource: TagSource,
) : TagRepository {
    override suspend fun getTag(): Flow<List<String>> = flow {
        emit(tagSource.getTagList())
    }

    override suspend fun getTagPost(): Flow<List<Post>> = flow {
        emit(tagSource.getTagPostList().tagPostDtoList.map { data -> data.toPostEntity() })
    }

    override suspend fun postAddTag(tag: String): Flow<Unit> = flow {
        emit(tagSource.postAddTag(tag))
    }

    override suspend fun deleteTag(tag: String): Flow<Unit> = flow {
        emit(tagSource.deleteTag(tag))
    }

    override suspend fun getPopularTag(): Flow<List<String>> = flow {
        emit(tagSource.getPopularTag())
    }
}
