package org.seonhwan.android.veloginmobile.ui.addtag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.seonhwan.android.veloginmobile.R
import org.seonhwan.android.veloginmobile.databinding.ItemAddtagPopularTagBinding
import org.seonhwan.android.veloginmobile.util.DiffCallback

class PopularTagAdapter : ListAdapter<String, PopularTagAdapter.PopularTagViewHolder>(diffUtil) {
    class PopularTagViewHolder(private val binding: ItemAddtagPopularTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(tag: String, position: Int) {
            with(binding) {
                tvAddtagPopularNumber.text = position.toString()
                if (position <= 3) {
                    tvAddtagPopularNumber.setTextColor(binding.root.context.getColor(R.color.main))
                }
                tvAddtagPopularTagName.text = tag
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTagViewHolder {
        return PopularTagViewHolder(
            ItemAddtagPopularTagBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: PopularTagViewHolder, position: Int) {
        holder.onBind(getItem(position), position + 1)
    }

    companion object {
        private val diffUtil = DiffCallback<String>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new },
        )
    }
}
