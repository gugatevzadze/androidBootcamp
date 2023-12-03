package com.example.homework_14


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_14.databinding.ItemOneBinding

class ItemRecyclerAdapter :
    ListAdapter<ItemData, ItemRecyclerAdapter.ItemViewHolder>(ItemDiffCallBack()) {

    inner class ItemViewHolder(val binding: ItemOneBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOneBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)

        holder.apply {
            binding.itemOneImage.setImageResource(item.image)
            binding.itemOneName.text = item.name
            binding.itemOneDesc.text = item.description

            //click listener for the delete button
            binding.itemOneDeleteBtn.setOnClickListener {
                //calling the onDeleteItemClick function when delete button is clicked
                onDeleteItemClick(item)
            }
        }
    }

    private class ItemDiffCallBack : DiffUtil.ItemCallback<ItemData>() {
        override fun areItemsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
            return oldItem.id == newItem.id
        }

        //i had error "Suspicious equality check: `equals()` is not implemented in ItemData"
        //i searched and the best way to prevent this message from happening was suppresing it
        //this way we tell the compiler that we are aware of the specific lint warning related to content comparison
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
            return oldItem == newItem
        }
    }

    //callback for delete button click
    var onDeleteItemClick: (ItemData) -> Unit = {}

    //function to set the onDeleteItemClick callback
    fun setOnDeleteItemClickListener(listener: (ItemData) -> Unit) {
        onDeleteItemClick = listener
    }
}

