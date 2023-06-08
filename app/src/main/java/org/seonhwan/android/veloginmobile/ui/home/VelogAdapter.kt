package org.seonhwan.android.veloginmobile.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.seonhwan.android.veloginmobile.databinding.ItemVelogBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.util.DiffCallback

class VelogAdapter(
    private val intentToWebView: (Post) -> Unit,
) : ListAdapter<Post, VelogAdapter.VelogViewHolder>(diffUtil) {

    class VelogViewHolder(private val binding: ItemVelogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(post: Post) {
            with(binding) {
                data = post
                ivVelogImg.load(post.img)
                rvVelogTag.adapter = VelogTagAdapter().apply {
                    if (post.tag.size > 3) {
                        submitList(post.tag.slice(0..2))
                    } else {
                        submitList(post.tag)
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
        holder.onBind(post)
    }

    companion object {
        private val diffUtil = DiffCallback<Post>(
            onItemsTheSame = { old, new -> old.summary == new.summary },
            onContentsTheSame = { old, new -> old == new },
        )

        const val VELOG = "velog"
    }
}
