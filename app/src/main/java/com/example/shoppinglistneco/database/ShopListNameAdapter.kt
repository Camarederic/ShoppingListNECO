package com.example.shoppinglistneco.database

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.ListNameItemBinding
import com.example.shoppinglistneco.entities.ShopListNameItem

class ShopListNameAdapter(private val listener: Listener) :
    ListAdapter<ShopListNameItem, ShopListNameAdapter.ItemHolder>(ItemComparator()) {


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ListNameItemBinding.bind(itemView)

        fun setData(shopListNameItem: ShopListNameItem, listener: Listener) = with(binding) {
            tvListName.text = shopListNameItem.name
            tvTime.text = shopListNameItem.time

            progressBar.max = shopListNameItem.allItemCounter
            progressBar.progress = shopListNameItem.checkedItemsCounter
            val colorState = ColorStateList.valueOf(getProgressColorState(shopListNameItem, binding.root.context))
            progressBar.progressTintList = colorState
            counterCardView.backgroundTintList = colorState

            val counterText = "${shopListNameItem.checkedItemsCounter}/${shopListNameItem.allItemCounter}"
            tvCounter.text = counterText

            itemView.setOnClickListener {
                listener.onClickItem(shopListNameItem)
            }
            imageButtonDeleteList.setOnClickListener {
                listener.deleteItem(shopListNameItem.id!!)
            }

            imageButtonEditList.setOnClickListener {
                listener.updateItem(shopListNameItem)
            }
        }

        private fun getProgressColorState(item:ShopListNameItem, context: Context):Int{
            return if (item.checkedItemsCounter == item.allItemCounter){
                ContextCompat.getColor(context, R.color.green)
            }else{
                ContextCompat.getColor(context,R.color.red_main)
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

    class ItemComparator : DiffUtil.ItemCallback<ShopListNameItem>() {
        override fun areItemsTheSame(
            oldItem: ShopListNameItem,
            newItem: ShopListNameItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShopListNameItem,
            newItem: ShopListNameItem
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
        fun updateItem(shopListNameItem: ShopListNameItem)
        fun onClickItem(shopListNameItem: ShopListNameItem)
    }


}