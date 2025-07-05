package mnshat.dev.myproject.auth.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentLangauageBinding
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.SplashActivity

@AndroidEntryPoint
class LanguageFragment : BaseBottomSheetDialogFragment() {

    private  val viewModel: AuthViewModel by viewModels()
    private  lateinit var binding: FragmentLangauageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLangauageBinding.inflate(inflater,container,false)
        setupClickListener()
        initializeViews()
        return  binding.root
    }


     fun setupClickListener() {
        binding.close.setOnClickListener{
            dismiss()
        }
    }

     fun initializeViews() {

        if (viewModel.currentLang.value == ENGLISH_KEY) {
            binding.rbEnglish.isChecked = true
            binding.rbArabic.textDirection = View.TEXT_DIRECTION_LTR

        }else{
            binding.rbArabic.isChecked = true
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))

        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private  fun observeViewModel(){
        viewModel.currentLang.observe(viewLifecycleOwner) {
            if (viewModel.currentLang.value != it){
                viewModel.sharedPreferences.storeString(LANGUAGE,it)
                startActivity(Intent(activity, SplashActivity::class.java))
                activity?.finish()
            }
        }

    }

}