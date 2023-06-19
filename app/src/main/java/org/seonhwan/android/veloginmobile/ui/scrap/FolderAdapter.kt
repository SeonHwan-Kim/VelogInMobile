package org.seonhwan.android.veloginmobile.ui.scrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.seonhwan.android.veloginmobile.data.local.model.Folder
import org.seonhwan.android.veloginmobile.databinding.ItemBottomSheetFolderBinding
import org.seonhwan.android.veloginmobile.util.DiffCallback

class FolderAdapter : ListAdapter<Folder, FolderAdapter.FolderViewHolder>(diffUtil) {
    class FolderViewHolder(private val binding: ItemBottomSheetFolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(folder: Folder) {
            binding.data = folder
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        return FolderViewHolder(
            ItemBottomSheetFolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = DiffCallback<Folder>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}
