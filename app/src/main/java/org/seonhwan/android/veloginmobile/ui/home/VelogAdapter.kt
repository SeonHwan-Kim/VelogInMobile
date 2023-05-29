package org.seonhwan.android.veloginmobile.presentation.home

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import org.seonhwan.android.veloginmobile.databinding.ItemVelogBinding
import org.seonhwan.android.veloginmobile.domain.entity.Post
import org.seonhwan.android.veloginmobile.ui.home.VelogTagAdapter
import org.seonhwan.android.veloginmobile.util.DiffCallback
import org.seonhwan.android.veloginmobile.util.extension.toPx

class VelogAdapter :
    ListAdapter<Post, VelogAdapter.VelogViewHolder>(diffUtil) {

    interface OnItemClickListener {
        fun onItemClick(pos: Int)
    }

    class VelogViewHolder(private val binding: ItemVelogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(post: Post) {
            with(binding) {
                data = post
                ivVelogImg.load(post.img) {
                    transformations(RoundedCornersTransformation(8.toPx(), 8.toPx()))
                }
                val tagList = post.tag
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
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = DiffCallback<Post>(
            onItemsTheSame = { old, new -> old.summary == new.summary },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}
