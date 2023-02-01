package com.example.shoppinglistneco.database

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.ListNameItemBinding
import com.example.shoppinglistneco.databinding.ShopLibraryListItemBinding
import com.example.shoppinglistneco.databinding.ShopListItemBinding
import com.example.shoppinglistneco.entities.ShopListNameItem
import com.example.shoppinglistneco.entities.ShopListItem

class ShopListItemAdapter(private val listener: Listener) :
    ListAdapter<ShopListItem, ShopListItemAdapter.ItemHolder>(ItemComparator()) {


    class ItemHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun setItemData(shopListItem: ShopListItem, listener: Listener) {
            val binding = ShopListItemBinding.bind(itemView)
            binding.apply {
                tvName.text = shopListItem.name
                tvInfo.text = shopListItem.itemInfo
                tvInfo.visibility = infoVisibility(shopListItem)
                checkBox.isChecked = shopListItem.itemChecked
                setPaintFlagAndColor(binding)
                checkBox.setOnClickListener {
                    listener.onClickItem(shopListItem.copy(itemChecked = checkBox.isChecked), CHECK_BOX)
                }
                imageButtonEditLibrary.setOnClickListener {
                    listener.onClickItem(shopListItem, EDIT)
                }
            }
        }

        fun setLibraryData(shopListItem: ShopListItem, listener: Listener) {
            val binding = ShopLibraryListItemBinding.bind(itemView)
            binding.tvName.text = shopListItem.name
            binding.imageButtonEditLibrary.setOnClickListener {
                listener.onClickItem(shopListItem, EDIT_LIBRARY_ITEM)
            }
            binding.imageButtonDelete.setOnClickListener {
                listener.onClickItem(shopListItem, DELETE_LIBRARY_ITEM)
            }
            itemView.setOnClickListener {
                listener.onClickItem(shopListItem, ADD)
            }
        }

        private fun setPaintFlagAndColor(binding: ShopListItemBinding) {
            if (binding.checkBox.isChecked) {
                binding.tvName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvInfo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvName.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.light_gray
                    )
                )
                binding.tvInfo.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.light_gray
                    )
                )
            } else {
                binding.tvName.paintFlags = Paint.ANTI_ALIAS_FLAG
                binding.tvInfo.paintFlags = Paint.ANTI_ALIAS_FLAG
                binding.tvName.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.black
                    )
                )
                binding.tvInfo.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.black
                    )
                )
            }
        }

        private fun infoVisibility(shopListItem: ShopListItem): Int {
            return if (shopListItem.itemInfo.isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        companion object {
            fun createShopItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_list_item, parent, false)
                )
            }

            fun createLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_library_list_item, parent, false)
                )
            }
        }


    }

    class ItemComparator : DiffUtil.ItemCallback<ShopListItem>() {
        override fun areItemsTheSame(
            oldItem: ShopListItem,
            newItem: ShopListItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShopListItem,
            newItem: ShopListItem
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
        fun onClickItem(shopListItem: ShopListItem, state: Int)
    }

    companion object {
        const val EDIT = 0
        const val CHECK_BOX = 1
        const val EDIT_LIBRARY_ITEM = 2
        const val DELETE_LIBRARY_ITEM = 3
        const val ADD = 4
    }

}