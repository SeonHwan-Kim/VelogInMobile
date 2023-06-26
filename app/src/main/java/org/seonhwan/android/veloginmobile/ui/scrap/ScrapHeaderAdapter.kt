package org.seonhwan.android.veloginmobile.ui.scrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.seonhwan.android.veloginmobile.databinding.ItemScrapHeaderBinding
import timber.log.Timber

class ScrapHeaderAdapter: Adapter<ScrapHeaderAdapter.ScrapHeaderViewHolder>() {
    class ScrapHeaderViewHolder(private val binding: ItemScrapHeaderBinding): ViewHolder(binding.root) {
        fun onBind(){
            binding.tvScrapHeader.setOnClickListener {
                Timber.d("ScrapHeader")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapHeaderViewHolder {
        return ScrapHeaderViewHolder(
            ItemScrapHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ScrapHeaderViewHolder, position: Int) {
        holder.onBind()
    }
}