package mnshat.dev.myproject.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment2
import mnshat.dev.myproject.databinding.FragmentLangauageBinding
import mnshat.dev.myproject.factories.AuthViewModelFactory
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.SplashActivity

class LanguageFragment : BaseBottomSheetDialogFragment2<FragmentLangauageBinding>() {
    lateinit var viewModel: AuthViewModel

    override fun setupClickListener() {
        binding.close.setOnClickListener{
            dismiss()
        }
    }

    override fun initializeViews() {

        if (currentLang == ENGLISH_KEY) {
            binding.rbEnglish.isChecked = true
            binding.rbArabic.textDirection = View.TEXT_DIRECTION_LTR

        }else{
            binding.rbArabic.isChecked = true
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))

        }
    }

    override fun getLayout() = R.layout.fragment_langauage

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = AuthViewModelFactory(sharedPreferences,activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[AuthViewModel::class.java]
        observeViewModel()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private  fun observeViewModel(){
        viewModel.currentLang.observe(viewLifecycleOwner) {
            if (currentLang != it){
                sharedPreferences.storeString(LANGUAGE,it)
                startActivity(Intent(activity, SplashActivity::class.java))
                activity?.finish()
            }
        }

    }

}