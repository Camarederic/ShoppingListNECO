package com.example.shoppinglistneco.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.NewListDialogBinding

object NewListDialog {

    fun showDialog(context: Context, listener: Listener, name: String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)

        binding.edNewListName.setText(name)
        if (name.isNotEmpty()){
            binding.tvTitle.text = context.getString(R.string.update_list_name)
            binding.buttonCreate.text = context.getString(R.string.update_button)
        }
        binding.buttonCreate.setOnClickListener {
            val listName = binding.edNewListName.text.toString()
            if (listName.isNotEmpty()) {
                listener.onClick(listName)
            }
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick(name: String)
    }
}