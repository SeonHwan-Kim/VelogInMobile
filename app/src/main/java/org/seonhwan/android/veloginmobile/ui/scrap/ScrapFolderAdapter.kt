package org.seonhwan.android.veloginmobile.ui.scrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.seonhwan.android.veloginmobile.data.local.model.Folder
import org.seonhwan.android.veloginmobile.databinding.ItemScrapFolderBinding
import org.seonhwan.android.veloginmobile.util.DiffCallback

class ScrapFolderAdapter(
    private val intentToFolderScrapPost: (String) -> Unit,
) : ListAdapter<Folder, ScrapFolderAdapter.ScrapFolderViewHolder>(diffUtil) {
    class ScrapFolderViewHolder(private val binding: ItemScrapFolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(folder: Folder) {
            binding.data = folder
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapFolderViewHolder {
        return ScrapFolderViewHolder(
            ItemScrapFolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: ScrapFolderViewHolder, position: Int) {
        val folder = getItem(position)
        holder.itemView.setOnClickListener {
            intentToFolderScrapPost(folder.name)
        }
        holder.onBind(folder)
    }

    companion object {
        private val diffUtil = DiffCallback<Folder>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.name == new.name },
        )
    }
}
