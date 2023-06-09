package org.seonhwan.android.veloginmobile.data.remote.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.seonhwan.android.veloginmobile.data.remote.source.TagSource
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.domain.repository.remote.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagSource: TagSource,
) : TagRepository {
    override suspend fun getTag(): Flow<List<String>> = flow {
        emit(tagSource.getTagList())
    }

    override suspend fun getTagPost(tag: String): Flow<List<Post>> = flow {
        emit(tagSource.getTagPostList(tag).map { data -> data.toPostEntity() })
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
