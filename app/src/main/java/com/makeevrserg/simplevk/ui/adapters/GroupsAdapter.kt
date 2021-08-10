package com.makeevrserg.simplevk.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.makeevrserg.simplevk.databinding.GroupListAdapterBinding
import com.vk.sdk.api.groups.dto.GroupsGroupFull


class GroupsAdapter(private val clickListener: GroupListener) :
    ListAdapter<GroupsGroupFull, GroupsAdapter.ViewHolder>(
        GroupDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: GroupListAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroupsGroupFull, clickListener: GroupListener) {
            binding.group = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GroupListAdapterBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class GroupDiffCallback : DiffUtil.ItemCallback<GroupsGroupFull>() {
        override fun areItemsTheSame(
            oldItem: GroupsGroupFull,
            newItem: GroupsGroupFull
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GroupsGroupFull,
            newItem: GroupsGroupFull
        ): Boolean {

            return oldItem.id==newItem.id
        }
    }

}

class GroupListener(val clickListener: (group: GroupsGroupFull, view: View) -> Unit) {
    fun onClick(group: GroupsGroupFull, view: View) = clickListener(group, view)

}