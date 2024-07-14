package mnshat.dev.myproject.auth

import android.os.Bundle
import android.view.View
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentLangauageBinding
import mnshat.dev.myproject.util.ENGLISH_KEY

class LanguageFragment : BaseBottomSheetDialogFragment<FragmentLangauageBinding>() {
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
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel
    }



}