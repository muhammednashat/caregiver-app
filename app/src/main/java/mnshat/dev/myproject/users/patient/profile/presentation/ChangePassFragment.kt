package mnshat.dev.myproject.users.patient.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentChangePassBinding
import mnshat.dev.myproject.util.isValidInput
import kotlin.getValue


class ChangePassFragment : BaseFragment(){

    private lateinit var currentPassword:String
    private lateinit var newPassword:String
    private lateinit var confirmNewPassword:String
    private  val viewModel:ProfileViewModel by activityViewModels()

   private lateinit var binding: FragmentChangePassBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePassBinding.inflate(inflater, container, false)
        setupClickListener()
        observeViewModel()
        return binding.root

    }



    private fun setupClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnConfirm.setOnClickListener {
           if( areInputsValid()) {
               checkInternetConnection()
           }
        }
    }
    private fun checkInternetConnection() {
        if (isConnected()) {
            showProgressDialog()
            viewModel.changeUserPassword(currentPassword,newPassword)
        } else {
            showNoInternetSnackBar(binding.root)
        }
    }

    private fun assignPasswordInputs() {
        currentPassword = binding.editCurrentPassword.text.toString()
        newPassword = binding.editNewPassword.text.toString()
        confirmNewPassword = binding.editConfirmNewPass.text.toString()
    }
    private fun areInputsValid(): Boolean {
        assignPasswordInputs()
        return if (!isValidInput(currentPassword)) {
            showToast(getString(R.string.enter_current_password))
            false
        } else if (!isValidInput(newPassword)) {
            showToast(getString(R.string.enter_new_password))
            false
        } else if (newPassword.trim().length <6) {
            showToast(getString(R.string.pass_error))
            false
        } else if (!isValidInput(confirmNewPassword)) {
            showToast(getString(R.string.confirm_new_password))
            false
        }else if (confirmNewPassword.trim().length < 6) {
            showToast(getString(R.string.pass_error))
            false
        } else if (confirmNewPassword != newPassword) {
            showToast(getString(R.string.passwords_do_not_match))
            false
        } else {
            true
        }
    }

    private fun observeViewModel(){
        viewModel.status.observe(viewLifecycleOwner){
            it?.let{
                if (it){
              showToast(getString(R.string.password_changed_successfully))
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