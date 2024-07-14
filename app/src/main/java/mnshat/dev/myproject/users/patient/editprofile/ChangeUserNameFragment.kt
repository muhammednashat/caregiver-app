package mnshat.dev.myproject.users.patient.editprofile

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentChangeUserNameBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.NAME
import mnshat.dev.myproject.util.USER_NAME
import mnshat.dev.myproject.util.isValidInput


class ChangeUserNameFragment : BaseEditProfileFragment<FragmentChangeUserNameBinding>() {
    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
        binding.edCurrentName.setText(sharedPreferences.getString(USER_NAME))
    }

    override fun getLayout() =
        R.layout.fragment_change_user_name

    override fun setupClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.confirmation.setOnClickListener {
            val newName = binding.edNewName.text.toString().trim()
            if (isValidInput(newName)) {
                updateName(NAME, newName)
            } else {
                showToast(getString(R.string.enter_the_new_name))
            }
        }
    }


    private fun updateName(key: String, name: String) {
        showProgressDialog()

        val map = mapOf<String, Any>(key to name)
        FirebaseService.updateItemsProfileUser(FirebaseService.userId, map) {
            if (it) {
                showToast(getString(R.string.the_username_has_been_changed_successfully))
                sharedPreferences.storeString(USER_NAME, name!!)
                findNavController().popBackStack()
            } else {
                showToast(getString(R.string.update_failed))
            }
            dismissProgressDialog()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissProgressDialog()
    }


}