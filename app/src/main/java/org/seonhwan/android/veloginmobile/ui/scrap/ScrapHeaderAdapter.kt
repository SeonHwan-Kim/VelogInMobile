package org.seonhwan.android.veloginmobile.ui.scrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.seonhwan.android.veloginmobile.databinding.ItemScrapHeaderBinding

class ScrapHeaderAdapter(
    private val onClickAddFolder: () -> Unit,
) : Adapter<ScrapHeaderAdapter.ScrapHeaderViewHolder>() {
    class ScrapHeaderViewHolder(private val binding: ItemScrapHeaderBinding) :
        ViewHolder(binding.root) {
        fun onBind(
            onClickAddFolder: () -> Unit,
        ) {
            binding.tvScrapHeader.setOnClickListener {
                onClickAddFolder()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapHeaderViewHolder {
        return ScrapHeaderViewHolder(
            ItemScrapHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ScrapHeaderViewHolder, position: Int) {
        holder.onBind(onClickAddFolder)
    }
}
