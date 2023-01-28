package com.example.shoppinglistneco.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.ListNameItemBinding
import com.example.shoppinglistneco.databinding.NoteListItemBinding
import com.example.shoppinglistneco.entities.NoteItem
import com.example.shoppinglistneco.entities.ShoppingListName
import com.example.shoppinglistneco.utils.HtmlManager

class ShopListNameAdapter(private val listener: Listener) :
    ListAdapter<ShoppingListName, ShopListNameAdapter.ItemHolder>(ItemComparator()) {


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ListNameItemBinding.bind(itemView)

        fun setData(shopListNameItem: ShoppingListName, listener: Listener) = with(binding) {
            tvListName.text = shopListNameItem.name
            tvTime.text = shopListNameItem.time

            itemView.setOnClickListener {

            }
            imageButtonDeleteList.setOnClickListener {
                listener.deleteItem(shopListNameItem.id!!)
            }

            imageButtonEditList.setOnClickListener {
                listener.updateItem(shopListNameItem)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_name_item, parent, false)
                )
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ShoppingListName>() {
        override fun areItemsTheSame(
            oldItem: ShoppingListName,
            newItem: ShoppingListName
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShoppingListName,
            newItem: ShoppingListName
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun updateItem(shopListName: ShoppingListName)
        fun onclickItem(shopListName: ShoppingListName)
    }


}