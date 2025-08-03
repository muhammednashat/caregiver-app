package mnshat.dev.myproject.base

import android.app.Dialog
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
@AndroidEntryPoint
open class BaseDialogFragment : DialogFragment() {

     lateinit var localDialog: Dialog

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
}