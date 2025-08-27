package mnshat.dev.myproject.users.patient.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentEditAgeBinding
import mnshat.dev.myproject.util.AGE_GROUP
import kotlin.getValue

class EditAgeFragment: BaseFragment() {

    private var  currentIntAge =0
    private lateinit var binding: FragmentEditAgeBinding
    private  val viewModel:ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAgeBinding.inflate(inflater, container, false)

        initializeViews()
        setupClickListener()
        observeViewModel()

        return binding.root

    }



    private fun initializeViews() {

        currentIntAge = viewModel.userProfile().ageGroup!!
        setChoosenAge(currentIntAge)
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

    private fun setupClickListener() {

        binding.groupRoot.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_young -> {
                    currentIntAge = 1
                }
                R.id.rb_middle_age -> {
                    currentIntAge = 2
                }
                R.id.rb_older -> {
                    currentIntAge = 3
                }
            }
        }
        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnConfirm.setOnClickListener {
           if (currentIntAge == viewModel.userProfile().ageGroup!!){
               findNavController().popBackStack()
           }else{
               checkInternetConnection()
           }
        }
    }

    private fun checkInternetConnection() {
        if (isConnected()) {
            showProgressDialog()
            viewModel.updateUserProfileRemotely(AGE_GROUP, currentIntAge)
        } else {
            showNoInternetSnackBar(binding.root)
        }
    }

    private fun observeViewModel(){
        viewModel.status.observe(viewLifecycleOwner){
            it?.let{
                if (it){
                showToast(getString(R.string.age_group_changed_successfully))
                    findNavController().popBackStack()
                }else{
                    showToast(getString(R.string.update_failed))
                }
                viewModel.restStatus()
                dismissProgressDialog()
            }
        }
    }

}