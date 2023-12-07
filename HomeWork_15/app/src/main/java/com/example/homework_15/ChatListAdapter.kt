package com.example.homework_15

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.homework_15.databinding.ChatItem1Binding
import com.example.homework_15.databinding.ChatItem2Binding
import com.example.homework_15.databinding.ChatItem3Binding

class ChatListAdapter :
    ListAdapter<ChatItem, RecyclerView.ViewHolder>(ChatItemDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_LAYOUT1 = 1
        private const val VIEW_TYPE_LAYOUT2 = 2
        private const val VIEW_TYPE_LAYOUT3 = 3
    }

    inner class ChatItemViewholder1(val binding: ChatItem1Binding) : RecyclerView.ViewHolder(binding.root)

    inner class ChatItemViewholder2(val binding: ChatItem2Binding) : RecyclerView.ViewHolder(binding.root)

    inner class ChatItemViewholder3(val binding: ChatItem3Binding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_LAYOUT1 -> {
                val binding = ChatItem1Binding.inflate(inflater, parent, false)
                ChatItemViewholder1(binding)
            }

            VIEW_TYPE_LAYOUT2 -> {
                val binding = ChatItem2Binding.inflate(inflater, parent, false)
                ChatItemViewholder2(binding)
            }

            VIEW_TYPE_LAYOUT3 -> {
                val binding = ChatItem3Binding.inflate(inflater, parent, false)
                ChatItemViewholder3(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder.itemViewType) {
            VIEW_TYPE_LAYOUT1 -> {
                val binding = (holder as ChatItemViewholder1).binding
                binding.apply {
                    itemName.text = item.owner
                    itemMessage.text = item.lastMessage
                    itemTime.text = item.lastActive
                    itemUnread.text = item.unreadMessages.toString()
                    isTyping.visibility = if (item.isTyping) View.VISIBLE else View.GONE
                    itemUnread.visibility = if (item.unreadMessages > 0) View.VISIBLE else View.INVISIBLE

                    if (!item.image.isNullOrBlank()) {
                        Glide.with(holder.itemView.context)
                            .load(item.image)
                            .transform(CenterCrop())
                            .into(itemImage)
                    } else {
                        Glide.with(holder.itemView.context)
                            .load(R.drawable.default_avatar)
                            .transform(CenterCrop())
                            .into(itemImage)
                    }
                }
            }

            VIEW_TYPE_LAYOUT2 -> {
                val binding = (holder as ChatItemViewholder2).binding
                binding.apply {
                    itemName.text = item.owner
                    itemMessage.text = item.lastMessage
                    itemTime.text = item.lastActive
                    itemUnread.text = item.unreadMessages.toString()
                    isTyping.visibility = if (item.isTyping) View.VISIBLE else View.GONE
                    itemUnread.visibility = if (item.unreadMessages > 0) View.VISIBLE else View.INVISIBLE

                    if (!item.image.isNullOrBlank()) {
                        Glide.with(holder.itemView.context)
                            .load(item.image)
                            .transform(CenterCrop())
                            .into(itemImage)
                    } else {
                        Glide.with(holder.itemView.context)
                            .load(R.drawable.default_avatar)
                            .transform(CenterCrop())
                            .into(itemImage)
                    }
                }
            }

            VIEW_TYPE_LAYOUT3 -> {
                val binding = (holder as ChatItemViewholder3).binding
                binding.apply {
                    itemName.text = item.owner
                    itemMessage.text = item.lastMessage
                    itemTime.text = item.lastActive
                    itemUnread.text = item.unreadMessages.toString()
                    isTyping.visibility = if (item.isTyping) View.VISIBLE else View.GONE
                    itemUnread.visibility = if (item.unreadMessages > 0) View.VISIBLE else View.INVISIBLE

                    if (!item.image.isNullOrBlank()) {
                        Glide.with(holder.itemView.context)
                            .load(item.image)
                            .transform(CenterCrop())
                            .into(itemImage)
                    } else {
                        Glide.with(holder.itemView.context)
                            .load(R.drawable.default_avatar)
                            .transform(CenterCrop())
                            .into(itemImage)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return when (item.lastMessageType) {
            ChatItem.ItemType.TEXT -> VIEW_TYPE_LAYOUT1
            ChatItem.ItemType.FILE -> VIEW_TYPE_LAYOUT2
            ChatItem.ItemType.VOICE -> VIEW_TYPE_LAYOUT3
        }
    }


    private class ChatItemDiffCallback : DiffUtil.ItemCallback<ChatItem>() {
        override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            return oldItem == newItem
        }
    }

    private var originalList: List<ChatItem> = emptyList()

    private var filteredList: List<ChatItem> = emptyList()

    fun search(searchWord: String) {
        filteredList = if (searchWord.isEmpty()) {
            originalList
        } else {
            originalList.filter { it.owner.contains(searchWord, ignoreCase = true) }
        }
        submitList(filteredList)
    }

    fun setOriginalList(list: List<ChatItem>) {
        originalList = list
        search("") // Reset the search when setting the original list
    }
}


