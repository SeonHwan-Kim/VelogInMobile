package org.seonhwan.android.veloginmobile.ui.addtag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.seonhwan.android.veloginmobile.databinding.ItemAddtagMyTagBinding
import org.seonhwan.android.veloginmobile.util.DiffCallback

class MyTagAdapter(
    private val onClickDeleteTag: OnClickDeleteTag,
) : ListAdapter<String, MyTagAdapter.MyTagAdapterViewHolder>(diffUtil) {
    class MyTagAdapterViewHolder(
        private val binding: ItemAddtagMyTagBinding,
        private val onClickDeleteTag: OnClickDeleteTag,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(tag: String) {
            binding.tvAddtagTagName.text = tag
            deleteTag(tag)
        }

        private fun deleteTag(tag: String) {
            binding.root.setOnClickListener {
                onClickDeleteTag.deleteTag(tag)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTagAdapterViewHolder {
        return MyTagAdapterViewHolder(
            ItemAddtagMyTagBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onClickDeleteTag,
        )
    }

    override fun onBindViewHolder(holder: MyTagAdapterViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = DiffCallback<String>(
            onItemsTheSame = { old, new -> old == new },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}
