package mnshat.dev.myproject.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.MyApplication
import mnshat.dev.myproject.util.SharedPreferencesManager

abstract class BaseBottomSheetDialogFragment<T : ViewDataBinding> : BottomSheetDialogFragment() {

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
        return binding.root
    }

    open fun setupClickListener(){}
    abstract fun initializeViews()
    abstract fun getLayout(): Int
    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = (requireActivity().application as MyApplication).sharedPreferences
        currentLang = sharedPreferences.getString(LANGUAGE)
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

