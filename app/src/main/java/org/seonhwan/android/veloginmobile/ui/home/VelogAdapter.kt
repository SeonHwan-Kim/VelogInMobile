package org.seonhwan.android.veloginmobile.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.seonhwan.android.veloginmobile.databinding.ItemVelogBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.util.DiffCallback
import org.seonhwan.android.veloginmobile.util.extension.BookmarkSnackbar

class VelogAdapter(
    private val intentToWebView: (Post) -> Unit,
    private val scrapPost: (Post) -> Unit,
    private val deleteScrapPost: (String) -> Unit,
    private val scrapPostList: List<Post>?,
) : ListAdapter<Post, VelogAdapter.VelogViewHolder>(diffUtil) {

    class VelogViewHolder(private val binding: ItemVelogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            post: Post,
            scrapPost: (Post) -> Unit,
            deleteScrapPost: (String) -> Unit,
            scrapPostList: List<Post>?,
        ) {
            with(binding) {
                data = post
                ivVelogImg.load(post.img)
                if (scrapPostList != null) {
                    if (scrapPostList.contains(post)) ibVelogBookmark.isSelected = true
                }
                Log.d("velogViewHolder", scrapPostList.toString())
                rvVelogTag.adapter = VelogTagAdapter().apply {
                    if (post.tag.size > 3) {
                        submitList(post.tag.slice(0..2))
                    } else {
                        submitList(post.tag)
                    }
                }
                onClickBookmark(post, scrapPost, deleteScrapPost)
            }
        }

        private fun onClickBookmark(
            post: Post,
            scrapPost: (Post) -> Unit,
            deleteScrapPost: (String) -> Unit,
        ) {
            with(binding) {
                ibVelogBookmark.setOnClickListener {
                    with(ibVelogBookmark) {
                        if (isSelected) {
                            deleteScrapPost(post.url)
                            BookmarkSnackbar.make(binding.root, "스크랩을 취소하였습니다.").show()
                        } else {
                            scrapPost(post)
                            BookmarkSnackbar.make(binding.root, "스크랩 했습니다.").show()
                        }
                        isSelected = !isSelected
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VelogViewHolder {
        return VelogViewHolder(
            ItemVelogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: VelogViewHolder, position: Int) {
        val post = getItem(position)
        holder.itemView.setOnClickListener {
            intentToWebView(post)
        }
        holder.onBind(post, scrapPost, deleteScrapPost, scrapPostList)
    }

    companion object {
        private val diffUtil = DiffCallback<Post>(
            onItemsTheSame = { old, new -> old.url == new.url },
            onContentsTheSame = { old, new -> old == new },
        )

        const val VELOG = "velog"
    }
}
