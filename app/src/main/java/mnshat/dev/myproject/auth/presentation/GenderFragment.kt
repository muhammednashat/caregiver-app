package mnshat.dev.myproject.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentGenderBinding
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.GENDER

@AndroidEntryPoint
class GenderFragment : BaseBottomSheetDialogFragment() {
    private  val viewModel: AuthViewModel by viewModels()


    private  lateinit var binding: FragmentGenderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenderBinding.inflate(inflater,container,false)
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
        if (viewModel.currentLang.value != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
        changeUserUi(viewModel.sharedPreferences.getInt(GENDER))
    }


  private fun observeViewModel(){
            viewModel.intGender.observe(viewLifecycleOwner) {
            viewModel.setStrGender(requireActivity(),viewModel.sharedPreferences,it)
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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        observeViewModel()
    }

}