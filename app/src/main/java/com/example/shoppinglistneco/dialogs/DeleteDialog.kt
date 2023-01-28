package com.example.shoppinglistneco.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.shoppinglistneco.databinding.DeleteDialogBinding
import com.example.shoppinglistneco.databinding.NewListDialogBinding

object DeleteDialog {

    fun showDialog(context: Context, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = DeleteDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)

        binding.buttonDelete.setOnClickListener {
            listener.onClick()
            dialog?.dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dialog?.dismiss()
        }

        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    interface Listener {
        fun onClick()
    }
}