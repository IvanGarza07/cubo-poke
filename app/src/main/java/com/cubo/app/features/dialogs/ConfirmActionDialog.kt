package com.cubo.app.features.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.cubo.app.databinding.DialogConfirmActionBinding


class ConfirmActionDialog(
    private val context: Context
) {

    private lateinit var dialog: Dialog
    private lateinit var binding: DialogConfirmActionBinding
    private lateinit var cancelButtonClickListener: (() -> Unit)
    private lateinit var confirmButtonClickListener: (() -> Unit)

    fun setOnCancelButtonClickListener(listener: (() -> Unit)) {
        cancelButtonClickListener = listener
    }

    fun setOnConfirmButtonClickListener(listener: (() -> Unit)) {
        confirmButtonClickListener = listener
    }

    fun startDialog() {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogConfirmActionBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        initViews()
    }

    fun showDialog() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }

    private fun initViews() {
        binding.buttonCancelAction.setOnClickListener {
            cancelButtonClickListener.invoke()
        }
        binding.buttonConfirmAction.setOnClickListener {
            confirmButtonClickListener.invoke()
        }
    }
}