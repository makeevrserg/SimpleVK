package com.makeevrserg.simplevk.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.makeevrserg.simplevk.databinding.FriendListAdapterBinding
import com.makeevrserg.simplevk.databinding.GroupListAdapterBinding
import com.vk.sdk.api.friends.dto.FriendsUserXtrLists
import com.vk.sdk.api.groups.dto.GroupsGroupFull


class FriendsAdapter(private val clickListener: FriendsListener) :
    ListAdapter<FriendsUserXtrLists, FriendsAdapter.ViewHolder>(
        GroupDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: FriendListAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FriendsUserXtrLists, clickListener: FriendsListener) {
            binding.friend = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FriendListAdapterBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class GroupDiffCallback : DiffUtil.ItemCallback<FriendsUserXtrLists>() {
        override fun areItemsTheSame(
            oldItem: FriendsUserXtrLists,
            newItem: FriendsUserXtrLists
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FriendsUserXtrLists,
            newItem: FriendsUserXtrLists
        ): Boolean {

            return oldItem.id==newItem.id
        }
    }

}

class FriendsListener(val clickListener: (group: FriendsUserXtrLists, view: View) -> Unit) {
    fun onClick(group: FriendsUserXtrLists, view: View) = clickListener(group, view)

}