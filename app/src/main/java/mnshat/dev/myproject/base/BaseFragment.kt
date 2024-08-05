package mnshat.dev.myproject.base

import android.app.Dialog
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
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.AuthActivity
import mnshat.dev.myproject.databinding.DialogBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.EMAIL
import mnshat.dev.myproject.util.IS_SECOND_TIME
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.MyApplication
import mnshat.dev.myproject.util.PASSWORD
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log

open abstract class BaseFragment<T:ViewDataBinding>: Fragment() {

    private lateinit var progressDialog: Dialog
    private lateinit var temporallyDialog: Dialog
    lateinit var currentLang:String
    lateinit var sharedPreferences: SharedPreferencesManager
    lateinit var binding: T

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        initializeViews()
        setupClickListener()
        log("onCreateView")

        return binding.root
    }

   open  fun setupClickListener(){}
    open  fun initializeViews(){}
    abstract fun getLayout(): Int
    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = (requireActivity().application as MyApplication).sharedPreferences
        currentLang = sharedPreferences.getString(LANGUAGE)
        progressDialog = Dialog(requireContext())
    }


    fun getTextAge( age: Int?):String? {
      return  when(age){
            1 ->  getString(R.string.young_adulthood)
            2 ->  getString(R.string.middle_age)
            3 ->  getString(R.string.older)
          else -> null
        }

    }

    fun getTextGender(gender: Int?) :String? {
      return  when(gender){
            1 -> getString(R.string.male)
            2 -> getString(R.string.female)
          else -> null
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
                showProgressDialog()
            callBack()
        }
        dialogBinding.icClose.setOnClickListener {
            temporallyDialog.dismiss()
        }
        temporallyDialog.show()
    }

    fun getTextDialect(dialect: Int?) :String? {
        return when(dialect){
            1 -> getString(R.string.standard_arabic)
            2->   getString(R.string.gulf_dialect)
            3 -> getString(R.string.syrian_dialect)
            4-> getString(R.string.iraqi_dialect)
            5-> getString(R.string.egyptian_dialect)
            else -> null
        }

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
    fun logOut(){
       FirebaseService.logOut()
        val email = sharedPreferences.getString(EMAIL)
        val password = sharedPreferences.getString(PASSWORD)
        val currentLang = sharedPreferences.getString(LANGUAGE)

        sharedPreferences.clearData()
        sharedPreferences.storeBoolean(IS_SECOND_TIME, true)
        sharedPreferences.storeString(
            PASSWORD,password
        )
        sharedPreferences.storeString(
            LANGUAGE,currentLang
        )
        sharedPreferences.storeString(
            EMAIL,email
        )
        val intent = Intent(requireActivity(), AuthActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }



}