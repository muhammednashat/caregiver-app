package mnshat.dev.myproject.users.patient.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentChangeUserNameBinding
import mnshat.dev.myproject.util.NAME
import mnshat.dev.myproject.util.isValidInput


class ChangeUserNameFragment : BaseFragment() {


    private lateinit var binding: FragmentChangeUserNameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChangeUserNameBinding.inflate(inflater, container, false)

        initializeViews()
        setupClickListener()
        return binding.root

    }



    private fun initializeViews() {

//        binding.edCurrentName.setText(sharedPreferences.getString(USER_NAME))
    }

    private fun setupClickListener() {
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
//        showProgressDialog()
//
//        val map = mapOf<String, Any>(key to name)
//        FirebaseService.updateItemsProfileUser(FirebaseService.userId, map) {
//            if (it) {
//                showToast(getString(R.string.the_username_has_been_changed_successfully))
//                sharedPreferences.storeString(USER_NAME, name!!)
//                findNavController().popBackStack()
//            } else {
//                showToast(getString(R.string.update_failed))
//            }
//            dismissProgressDialog()
//
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissProgressDialog()
    }


}