package org.seonhwan.android.veloginmobile.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.seonhwan.android.veloginmobile.data.model.response.ResponsePostDto
import org.seonhwan.android.veloginmobile.data.model.response.ResponseTagPostDto
import org.seonhwan.android.veloginmobile.databinding.ItemVelogBinding

class VelogAdapter(context: Context) :
    ListAdapter<ResponseTagPostDto<ResponsePostDto>, VelogAdapter.VelogViewHolder>(VelogDiffCallBack()) {
    private val inflater by lazy { LayoutInflater.from(context) }

    class VelogViewHolder(private val binding: ItemVelogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(post: ResponseTagPostDto<ResponsePostDto>) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VelogViewHolder {
        val binding = ItemVelogBinding.inflate(inflater, parent, false)
        return VelogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VelogViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class VelogDiffCallBack : DiffUtil.ItemCallback<ResponseTagPostDto<ResponsePostDto>>() {
    override fun areItemsTheSame(
        oldItem: ResponseTagPostDto<ResponsePostDto>,
        newItem: ResponseTagPostDto<ResponsePostDto>,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ResponseTagPostDto<ResponsePostDto>,
        newItem: ResponseTagPostDto<ResponsePostDto>,
    ): Boolean {
        return oldItem == newItem
    }
}
