package org.seonhwan.android.veloginmobile.ui.scrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.seonhwan.android.veloginmobile.data.local.model.Folder
import org.seonhwan.android.veloginmobile.databinding.ItemBottomSheetFolderBinding
import org.seonhwan.android.veloginmobile.util.DiffCallback

class FolderAdapter(
    private val bottomSheet: BottomSheetDialogFragment,
    private val addScrapPostFolder: (String) -> Unit,
    private val updateFolder: (Folder) -> Unit,
) : ListAdapter<Folder, FolderAdapter.FolderViewHolder>(diffUtil) {
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
        val folder = getItem(position)
        holder.onBind(folder)
        holder.itemView.setOnClickListener {
            val increaseFolderSize = folder.copy(size = folder.size + 1)
            addScrapPostFolder(folder.name)
            updateFolder(increaseFolderSize)
            bottomSheet.dismiss()
        }
    }

    companion object {
        private val diffUtil = DiffCallback<Folder>(
            onItemsTheSame = { old, new -> old.name == new.name },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}
