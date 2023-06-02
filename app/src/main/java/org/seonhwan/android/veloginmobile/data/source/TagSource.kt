package org.seonhwan.android.veloginmobile.data.source

import org.seonhwan.android.veloginmobile.data.model.response.ResponsePostDto
import org.seonhwan.android.veloginmobile.data.model.response.ResponseTagPostDto
import org.seonhwan.android.veloginmobile.data.service.TagService
import javax.inject.Inject

class TagSource @Inject constructor(
    private val tagService: TagService,
) {
    suspend fun getTagList(): List<String> =
        tagService.getTag()

    suspend fun getTagPostList(): ResponseTagPostDto<ResponsePostDto> =
        tagService.getTagPost()

    suspend fun postAddTag(tag: String): Unit =
        tagService.postAddTag(tag)

    suspend fun deleteTag(tag: String): Unit =
        tagService.deleteTag(tag)
}
