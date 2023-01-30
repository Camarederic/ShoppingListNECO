package com.example.shoppinglistneco.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.databinding.EditListItemDialogBinding
import com.example.shoppinglistneco.databinding.NewListDialogBinding
import com.example.shoppinglistneco.entities.ShopListItem

object EditListItemDialog {

    fun showDialog(context: Context, item: ShopListItem, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = EditListItemDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)

        binding.edName.setText(item.name)
        binding.edItemInfo.setText(item.itemInfo)
        binding.buttonUpdate.setOnClickListener {
            if (binding.edName.text.toString().isNotEmpty()) {

                listener.onClick(
                    item.copy(
                        name = binding.edName.text.toString(),
                        itemInfo = binding.edItemInfo.text.toString()
                    )
                )
            }
            dialog?.dismiss()
        }



        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick(item: ShopListItem)
    }
}