package org.seonhwan.android.veloginmobile.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.seonhwan.android.veloginmobile.databinding.ItemVelogBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.ui.webview.WebViewActivity
import org.seonhwan.android.veloginmobile.util.DiffCallback

class VelogAdapter : ListAdapter<Post, VelogAdapter.VelogViewHolder>(diffUtil) {

    class VelogViewHolder(private val binding: ItemVelogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(post: Post) {
            with(binding) {
                data = post
                ivVelogImg.load(post.img)
                val tagList = post.tag
                rvVelogTag.adapter = VelogTagAdapter().apply {
                    if (post.tag.size > 3) {
                        submitList(post.tag.slice(0..2))
                    } else {
                        submitList(post.tag)
                    }
                }
                root.setOnClickListener {
                    intentToWebView(post)
                }
            }
        }

        private fun intentToWebView(post: Post) {
            with(binding) {
                val intent = Intent(root.context, WebViewActivity::class.java)
//                intent.putExtra(VELOG_URL, url
                intent.putExtra(VELOG, post)
                root.context.startActivity(intent)
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
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = DiffCallback<Post>(
            onItemsTheSame = { old, new -> old.summary == new.summary },
            onContentsTheSame = { old, new -> old == new },
        )

        const val VELOG = "velog"
        const val VELOG_URL = "url"
    }
}
