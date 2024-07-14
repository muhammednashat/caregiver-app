package mnshat.dev.myproject.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import mnshat.dev.myproject.util.MyApplication
import mnshat.dev.myproject.auth.SharedAuthViewModel
import mnshat.dev.myproject.util.SplashActivity
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.SharedPreferencesManager

open abstract class BaseBottomSheetDialogFragment<T:ViewDataBinding> : BottomSheetDialogFragment()  {

    lateinit var _viewModel: SharedAuthViewModel
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

    abstract fun setupClickListener()
    abstract fun initializeViews()
    abstract fun getLayout(): Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = (requireActivity().application as MyApplication).sharedPreferences
        currentLang = sharedPreferences.getString(LANGUAGE)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _viewModel = ViewModelProvider(requireActivity()).get(SharedAuthViewModel::class.java)
        observing()
    }

   open  fun observing(){
        _viewModel.currentLang.observe(viewLifecycleOwner) {
            if (currentLang != it){
                sharedPreferences.storeString(LANGUAGE,it)
                startActivity(Intent(activity, SplashActivity::class.java))
                activity?.finish()
            }
        }

    }







}

