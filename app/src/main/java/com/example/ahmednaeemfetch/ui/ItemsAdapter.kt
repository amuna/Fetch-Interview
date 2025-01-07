package com.example.ahmednaeemfetch.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ahmednaeemfetch.R
import com.example.ahmednaeemfetch.network.Item

class ItemsAdapter(
    private var items: List<Item>
) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.name)

        fun bind(item: Item) {
            nameTextView.text = item.name
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatedItems(newItems: List<Item>) {
        items = newItems
        // Using notifyDataSetChanged() because the entire dataset is being replaced.
        // DiffUtil is unnecessary in this case.
        notifyDataSetChanged()
    }
}

