package com.example.shoppinglistneco.database

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.NoteListItemBinding
import com.example.shoppinglistneco.entities.NoteItem
import com.example.shoppinglistneco.utils.HtmlManager
import com.example.shoppinglistneco.utils.TimeManager

class NoteAdapter(private val listener: Listener, private val defPref: SharedPreferences) :
    ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemComparator()) {


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = NoteListItemBinding.bind(itemView)

        fun setData(note: NoteItem, listener: Listener, defPref: SharedPreferences) = with(binding) {
            tvTitle.text = note.title
            tvDescription.text = HtmlManager.getTextFromHtml(note.content).trim()
            tvTime.text = TimeManager.getTimeFormat(note.time, defPref)
            itemView.setOnClickListener {
                listener.onclickItem(note)
            }
            imageButtonDelete.setOnClickListener {
                listener.deleteItem(note.id!!)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.note_list_item, parent, false)
                )
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<NoteItem>() {
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener, defPref)
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun onclickItem(note: NoteItem)
    }


}