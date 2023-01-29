package com.example.shoppinglistneco.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.ListNameItemBinding
import com.example.shoppinglistneco.entities.ShopListNameItem
import com.example.shoppinglistneco.entities.ShoppingListItem

class ShopListItemAdapter(private val listener: Listener) :
    ListAdapter<ShoppingListItem, ShopListItemAdapter.ItemHolder>(ItemComparator()) {


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ListNameItemBinding.bind(itemView)

        fun setItemData(shopListItem: ShoppingListItem, listener: Listener) = with(binding) {

        }

        fun setLibraryData(shopListItem: ShoppingListItem, listener: Listener) = with(binding) {

        }

        companion object {
            fun createShopItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_name_item, parent, false)
                )
            }

            fun createLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_name_item, parent, false)
                )
            }
        }


    }

    class ItemComparator : DiffUtil.ItemCallback<ShoppingListItem>() {
        override fun areItemsTheSame(
            oldItem: ShoppingListItem,
            newItem: ShoppingListItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShoppingListItem,
            newItem: ShoppingListItem
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return if (viewType == 0) {
            ItemHolder.createShopItem(parent)
        } else {
            ItemHolder.createLibraryItem(parent)
        }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (getItem(position).itemType == 0) {
            holder.setItemData(getItem(position), listener)
        } else {
            holder.setLibraryData(getItem(position), listener)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun updateItem(shopListNameItem: ShopListNameItem)
        fun onClickItem(shopListNameItem: ShopListNameItem)
    }


}