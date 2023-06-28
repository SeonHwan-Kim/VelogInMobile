package org.seonhwan.android.veloginmobile.ui.Subscribe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.seonhwan.android.veloginmobile.databinding.ItemSubscribeBinding
import org.seonhwan.android.veloginmobile.domain.entity.Subscriber
import org.seonhwan.android.veloginmobile.util.DiffCallback

class SubscriberAdapter :
    ListAdapter<Subscriber, SubscriberAdapter.SubscriberViewHolder>(diffUtil) {
    class SubscriberViewHolder(private val binding: ItemSubscribeBinding) :
        ViewHolder(binding.root) {
        fun onBind(subscriber: Subscriber) {
            binding.data = subscriber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        return SubscriberViewHolder(
            ItemSubscribeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val diffUtil = DiffCallback<Subscriber>(
            onItemsTheSame = { old, new -> old == new },
            onContentsTheSame = { old, new -> old.name == new.name },
        )
    }
}