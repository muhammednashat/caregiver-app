package mnshat.dev.myproject.users.patient.editprofile

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentChangePassBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.PASSWORD
import mnshat.dev.myproject.util.isValidInput


class ChangePassFragment : BaseEditProfileFragment<FragmentChangePassBinding>() {

    private lateinit var currentPassword:String
    private lateinit var newPassword:String
    private lateinit var confirmNewPassword:String
    override fun getLayout() = R.layout.fragment_change_pass
    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    override fun setupClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnConfirm.setOnClickListener {
           if( isValidation()) changePass()
        }
    }
    private fun retrievePasswordInputs() {
        currentPassword = binding.editCurrentPassword.text.toString()
        newPassword = binding.editNewPassword.text.toString()
        confirmNewPassword = binding.editConfirmNewPass.text.toString()
    }
    private fun isValidation(): Boolean {
        retrievePasswordInputs()
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
    private fun changePass() {
        showProgressDialog()
      FirebaseService.changeCurrentPassword(currentPassword,newPassword,requireActivity()){
          if (it == null){
              sharedPreferences.storeString(PASSWORD,newPassword)
              showToast(getString(R.string.password_changed_successfully))
              findNavController().popBackStack()
          }
          else{
              showToast(it)
          }
          dismissProgressDialog()
      }

    }





}