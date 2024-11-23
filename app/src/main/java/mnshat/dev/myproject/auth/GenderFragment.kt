package mnshat.dev.myproject.auth

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment2
import mnshat.dev.myproject.databinding.FragmentGenderBinding
import mnshat.dev.myproject.factories.AuthViewModelFactory
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.GENDER

class GenderFragment  :  BaseBottomSheetDialogFragment2<FragmentGenderBinding>() {
    lateinit var viewModel: AuthViewModel

    override fun setupClickListener() {
        binding.close.setOnClickListener{
            dismiss()
        }
    }
    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
        changeUserUi(sharedPreferences.getInt(GENDER))
    }
    override fun getLayout()= R.layout.fragment_gender

  private fun observeViewModel(){
        viewModel.intGender.observe(viewLifecycleOwner) {
            viewModel.setStrGender(requireActivity(),sharedPreferences,it)
            changeUserUi(it)
        }
    }
    private fun changeUserUi(type: Int?){
        when (type) {
            1 -> {
                binding.male.setBackgroundResource(R.drawable.corner_four_dark_blue)
                binding.maleText.setTextColor(resources.getColor(R.color.white))
                binding.female.setBackgroundResource(R.drawable.corner_four_gray)
                binding.femaleText.setTextColor(resources.getColor(R.color.dark_gray))
                binding.maleStatusCircle.visibility = View.GONE
                binding.maleStatusChecked.visibility = View.VISIBLE
                binding.femaleStatusChecked.visibility = View.GONE
                binding.femaleStatusCircle.visibility = View.VISIBLE
            }
            2 -> {
                binding.female.setBackgroundResource(R.drawable.corner_four_dark_blue)
                binding.femaleText.setTextColor(resources.getColor(R.color.white))
                binding.male.setBackgroundResource(R.drawable.corner_four_gray)
                binding.maleText.setTextColor(resources.getColor(R.color.dark_gray))
                binding.femaleStatusCircle.visibility = View.GONE
                binding.femaleStatusChecked.visibility = View.VISIBLE
                binding.maleStatusChecked.visibility = View.GONE
                binding.maleStatusCircle.visibility = View.VISIBLE
            }
        }

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = AuthViewModelFactory(sharedPreferences,activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[AuthViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        observeViewModel()
    }

}