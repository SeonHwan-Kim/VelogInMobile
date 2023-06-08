package org.seonhwan.android.veloginmobile.data.source

import org.seonhwan.android.veloginmobile.data.model.response.ResponsePostDto
import org.seonhwan.android.veloginmobile.data.model.response.ResponseTagPostDto
import org.seonhwan.android.veloginmobile.data.service.TagService
import javax.inject.Inject

class TagSource @Inject constructor(
    private val tagService: TagService,
) {
    suspend fun getTagList() =
        tagService.getTag()

    suspend fun getTagPostList() =
        tagService.getTagPost()

    suspend fun postAddTag(tag: String) =
        tagService.postAddTag(tag)

    suspend fun deleteTag(tag: String) =
        tagService.deleteTag(tag)

    suspend fun getPopularTag() =
        tagService.getPopularPost()
}
