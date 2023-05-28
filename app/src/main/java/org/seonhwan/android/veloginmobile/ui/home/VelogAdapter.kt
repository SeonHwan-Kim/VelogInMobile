package org.seonhwan.android.veloginmobile.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.seonhwan.android.veloginmobile.data.model.response.ResponsePostDto
import org.seonhwan.android.veloginmobile.databinding.ItemVelogBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.ui.home.VelogTagAdapter
import org.seonhwan.android.veloginmobile.util.DiffCallback

class VelogAdapter :
    ListAdapter<Post, VelogAdapter.VelogViewHolder>(diffUtil) {
    class VelogViewHolder(private val binding: ItemVelogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(post: Post) {
            binding.data = post
            Glide.with(binding.root)
                .load(post.img)
                .into(binding.ivVelogImg)
            val tagList = post.tag
            binding.rvVelogTag.adapter = VelogTagAdapter().apply {
                if (post.tag.size > 3) {
                    submitList(post.tag.slice(0..2))
                } else {
                    submitList(post.tag)
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
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = DiffCallback<Post>(
            onItemsTheSame = { old, new -> old.summary == new.summary },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}
