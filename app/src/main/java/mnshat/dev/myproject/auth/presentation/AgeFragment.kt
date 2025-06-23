package mnshat.dev.myproject.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentAgeBinding
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.ENGLISH_KEY

@AndroidEntryPoint
class AgeFragment : BaseBottomSheetDialogFragment() {
    private  val viewModel: AuthViewModel by viewModels()
    private  lateinit var binding: FragmentAgeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAgeBinding.inflate(inflater,container,false)
        setupClickListener()
        initializeViews()
        return  binding.root
    }

     fun setupClickListener() {
        binding.close.setOnClickListener {
            dismiss()
        }
    }

     fun initializeViews() {
        if (viewModel.currentLang.value != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
        setChoosenAge(viewModel.sharedPreferences.getInt(AGE_GROUP))
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

    private fun observeViewModel() {
        viewModel.intAge.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.setStrAge(requireActivity(),viewModel.sharedPreferences,it)
                setChoosenAge(it)
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