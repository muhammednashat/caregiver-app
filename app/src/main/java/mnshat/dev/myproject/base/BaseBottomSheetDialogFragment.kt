package mnshat.dev.myproject.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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



}

