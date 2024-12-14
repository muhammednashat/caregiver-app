package mnshat.dev.myproject

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.databinding.DialogBinding

@AndroidEntryPoint
open class BaseFragment: Fragment() {

    private lateinit var progressDialog: Dialog
    private lateinit var temporallyDialog: Dialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        progressDialog = Dialog(requireContext())
    }

     fun showProgressDialog() {
         if (progressDialog.ownerActivity == null){
             progressDialog = Dialog(requireContext())
         }
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.setCanceledOnTouchOutside(false)
        val window = progressDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.show()
    }

    fun dismissProgressDialog() {
        progressDialog.dismiss()
    }

    fun showToast(message: String) {
        val layoutInflater = layoutInflater
        val layout = layoutInflater.inflate(R.layout.custom_toast_layout, null)
        val textViewMessage = layout.findViewById<TextView>(R.id.text)
        textViewMessage.text = message
        with(Toast(context)) {
            setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
            duration = Toast.LENGTH_SHORT
            view = layout
            show()
        }
    }
    fun showTemporallyDialog(title: String, message: String,imageResource:Int?=null ,textButton: String,callBack:() -> Unit) {
        temporallyDialog = Dialog(requireContext())
        temporallyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogBinding.inflate(layoutInflater)
        dialogBinding.title.text = title
        dialogBinding.title.text = title
        imageResource?.let {dialogBinding.image.setImageResource(it) }
        dialogBinding.message.text = message
        dialogBinding.button.text = textButton
        temporallyDialog.setContentView(dialogBinding.root)
        temporallyDialog.setCanceledOnTouchOutside(true)
        val window = temporallyDialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        temporallyDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBinding.button.setOnClickListener {
            temporallyDialog.dismiss()
//                showProgressDialog()
            callBack()
        }
        dialogBinding.icClose.setOnClickListener {
            temporallyDialog.dismiss()
        }
        temporallyDialog.show()
    }

}