package com.victoris.canvas_drawing

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button

class DeleteConfirmationDialog {
    fun showDialog(
        context: Context,
        paintView: PaintView
    ) {
        val dialog = Dialog(context)

        // set dialog properties
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_delete_confirmation)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // widget initialization
        val btnYes: Button = dialog.findViewById(R.id.btn_yes)
        val btnNo: Button = dialog.findViewById(R.id.btn_no)

        btnYes.setOnClickListener {
            // delete item
            paintView.clearCanvas()

            // dialog dismiss
            dialog.dismiss()

        }
        btnNo.setOnClickListener { dialog.dismiss() }

        // show the dialog
        dialog.show()
    }
}