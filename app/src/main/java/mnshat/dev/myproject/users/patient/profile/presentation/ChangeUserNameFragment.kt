package mnshat.dev.myproject.users.patient.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentChangeUserNameBinding
import mnshat.dev.myproject.util.NAME
import mnshat.dev.myproject.util.isValidInput


class ChangeUserNameFragment : BaseFragment() {


    private lateinit var binding: FragmentChangeUserNameBinding
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChangeUserNameBinding.inflate(inflater, container, false)

        initializeViews()
        setupClickListener()
        observeViewModel()
        return binding.root
    }

    private fun initializeViews() {
        binding.edCurrentName.setText(viewModel.userProfile().name)
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
                checkInternetConnection(newName)

            } else {
                showToast(getString(R.string.enter_the_new_name))
            }
        }
    }

    private fun checkInternetConnection(newName: String) {
        if (isConnected()) {
            showProgressDialog()
            viewModel.updateUserProfileRemotely(NAME, newName)
        } else {
            showNoInternetSnackBar(binding.root)
        }
    }

    private fun observeViewModel() {
        viewModel.status.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    showToast(getString(R.string.the_username_has_been_changed_successfully))
                    findNavController().popBackStack()

                } else {
                    showToast(getString(R.string.update_failed))
                }
                viewModel.restStatus()
                dismissProgressDialog()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissProgressDialog()
    }


}