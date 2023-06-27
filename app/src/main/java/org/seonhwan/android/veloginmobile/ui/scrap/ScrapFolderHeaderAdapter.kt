package org.seonhwan.android.veloginmobile.ui.scrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.seonhwan.android.veloginmobile.databinding.ItemScrapPostHeaderBinding

class ScrapFolderHeaderAdapter(
    private val onClickDeleteButton: () -> Unit,
    private val onClickChangeFolderNameButton: () -> Unit,
) : RecyclerView.Adapter<ScrapFolderHeaderAdapter.ScrapFolderHeaderViewHolder>() {
    class ScrapFolderHeaderViewHolder(private val binding: ItemScrapPostHeaderBinding) :
        ViewHolder(binding.root) {
        fun onBind(
            onClickChangeFolderNameButton: () -> Unit,
            onClickDeleteButton: () -> Unit,
        ) {
            binding.tvScrapPostChangeFolderName.setOnClickListener {
                onClickChangeFolderNameButton()
            }
            binding.tvScrapPostDeleteFolder.setOnClickListener {
                onClickDeleteButton()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapFolderHeaderViewHolder {
        return ScrapFolderHeaderViewHolder(
            ItemScrapPostHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: ScrapFolderHeaderViewHolder, position: Int) {
        holder.onBind(onClickChangeFolderNameButton, onClickDeleteButton)
    }
}
