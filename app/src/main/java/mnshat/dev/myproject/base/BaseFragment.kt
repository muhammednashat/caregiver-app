package mnshat.dev.myproject.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.presentation.AuthActivity
import mnshat.dev.myproject.dataSource.room.AppDatabase
import mnshat.dev.myproject.databinding.DialogBinding
import mnshat.dev.myproject.databinding.DialogConfirmLogoutBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.IS_SECOND_TIME
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.PASSWORD
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USER_EMAIL
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
open class BaseFragment: Fragment() {

    private lateinit var progressDialog: Dialog
    private lateinit var temporallyDialog: Dialog
    lateinit var sharedDialog: Dialog

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

    fun showNoInternetSnackBar(view: View) {
        Snackbar.make(view, getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG)
            .show()
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

    fun showDialogConfirmLogout(sharedPreferences: SharedPreferencesManager) {
        sharedDialog = Dialog(requireContext())
        sharedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogConfirmLogoutBinding.inflate(layoutInflater)
        sharedDialog.setContentView(dialogBinding.root)
        sharedDialog.setCanceledOnTouchOutside(true)

        val window = sharedDialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }
        dialogBinding.icClose.setOnClickListener {
            sharedDialog.dismiss()
        }
        dialogBinding.btnLogout.setOnClickListener {
            logOut(sharedPreferences)
            sharedDialog.dismiss()
        }
        dialogBinding.btnCancel.setOnClickListener {
            sharedDialog.dismiss()
        }
        sharedDialog.show()
    }

    private fun logOut(sharedPreferences: SharedPreferencesManager) {
        AppDatabase.getDatabase(requireActivity()).close()
        val result = requireActivity().deleteDatabase("database-name")
        if (result) {
            log("database deleted")
        } else {
            log("database not deleted")
        }
        FirebaseService.logOut()
        val email = sharedPreferences.getString(USER_EMAIL)
        val password = sharedPreferences.getString(PASSWORD)
        val currentLang = sharedPreferences.getString(LANGUAGE)
        sharedPreferences.clearData()
        sharedPreferences.storeBoolean(IS_SECOND_TIME, true)
        sharedPreferences.storeString(
            PASSWORD, password
        )
        sharedPreferences.storeString(
            LANGUAGE, currentLang
        )
        sharedPreferences.storeString(
            USER_EMAIL, email
        )
        val intent = Intent(requireActivity(), AuthActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }


}