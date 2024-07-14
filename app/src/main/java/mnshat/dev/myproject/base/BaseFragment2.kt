package mnshat.dev.myproject.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import mnshat.dev.myproject.util.MyApplication
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.AuthActivity
import mnshat.dev.myproject.util.EMAIL
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.PASSWORD
import mnshat.dev.myproject.util.SharedPreferencesManager

private const val TAG = "BaseFragment"

open abstract class BaseFragment2 : Fragment() {
    private lateinit var progressDialog: Dialog
    lateinit var currentLang:String
     val usersReference = FirebaseDatabase.getInstance().getReference("users")
    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var sharedPreferences: SharedPreferencesManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = (requireActivity().application as MyApplication).sharedPreferences
        currentLang = sharedPreferences.getString(LANGUAGE)
        progressDialog = Dialog(requireContext())
    }
    fun logOut(){
        Firebase.auth.signOut()
        val email = sharedPreferences.getString(EMAIL)
        val password = sharedPreferences.getString(PASSWORD)
        sharedPreferences.clearData()
        sharedPreferences.storeString(
            PASSWORD,password
        )
        sharedPreferences.storeString(
            EMAIL,email
        )

        val intent = Intent(requireActivity(), AuthActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }



    fun isValidInput(input: String?): Boolean {
        return !TextUtils.isEmpty(input)
    }
     fun showProgressDialog() {
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








}