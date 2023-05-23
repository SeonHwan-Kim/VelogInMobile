package org.seonhwan.android.veloginmobile.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.seonhwan.android.veloginmobile.databinding.ItemVelogTagBinding
import org.seonhwan.android.veloginmobile.util.DiffCallback

class VelogTagAdapter : ListAdapter<String, VelogTagAdapter.VelogTagViewHolder>(diffUtil) {
    class VelogTagViewHolder(private val binding: ItemVelogTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(tag: String) {
            binding.tvVelogTagName.text = tag
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VelogTagViewHolder {
        return VelogTagViewHolder(
            ItemVelogTagBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: VelogTagViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = DiffCallback<String>(
            onItemsTheSame = { old, new -> old == new },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}
