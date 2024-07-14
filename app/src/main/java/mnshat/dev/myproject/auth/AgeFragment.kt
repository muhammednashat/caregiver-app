package mnshat.dev.myproject.auth

import android.os.Bundle
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentAgeBinding
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.ENGLISH_KEY


class AgeFragment : BaseBottomSheetDialogFragment<FragmentAgeBinding>() {


    override fun setupClickListener() {
        binding.close.setOnClickListener {
            dismiss()
        }
    }

    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
        setChoosenAge(sharedPreferences.getInt(AGE_GROUP))
    }

    private fun setChoosenAge(age: Int?) {
        age?.let {
            when (age) {
               1 -> binding.rbYoung.isChecked = true
               2-> binding.rbMiddleAge.isChecked = true
               3 -> binding.rbOlder.isChecked = true
            }
        }
    }


    override fun getLayout() = R.layout.fragment_age
    override fun observing() {
        super.observing()
        _viewModel.intAge.observe(viewLifecycleOwner) {
            it?.let {
                _viewModel.setStrAge(requireActivity(),sharedPreferences,it)
                setChoosenAge(it)
            }
        }
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel
    }


}