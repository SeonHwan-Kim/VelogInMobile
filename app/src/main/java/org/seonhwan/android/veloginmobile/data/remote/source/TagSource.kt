package org.seonhwan.android.veloginmobile.data.remote.source

import org.seonhwan.android.veloginmobile.data.remote.service.TagService
import javax.inject.Inject

class TagSource @Inject constructor(
    private val tagService: TagService,
) {
    suspend fun getTagList() =
        tagService.getTag()

    suspend fun getTagPostList(tag: String) =
        tagService.getTagPost(tag)

    suspend fun postAddTag(tag: String) =
        tagService.postAddTag(tag)

    suspend fun deleteTag(tag: String) =
        tagService.deleteTag(tag)

    suspend fun getPopularTag() =
        tagService.getPopularPost()
}
