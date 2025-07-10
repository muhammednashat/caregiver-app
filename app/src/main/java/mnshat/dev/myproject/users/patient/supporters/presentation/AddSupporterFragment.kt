package mnshat.dev.myproject.users.patient.supporters.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentAddSupporterBinding
import mnshat.dev.myproject.util.log

@AndroidEntryPoint

class AddSupporterFragment : BaseFragment() {

    private lateinit var binding: FragmentAddSupporterBinding
    private val viewModel: SupporterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddSupporterBinding.inflate(inflater, container, false)
        setupClickListener()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setTextCode()
        isInvitationUsed()
    }


    private fun isInvitationUsed() {
        if (viewModel.userProfile().invitationUsed!!) {
            binding.txLabelCode.text = getString(R.string.code_already_used)
            binding.btnCopy.alpha = 0.0f
            binding.btnEditCode.alpha = 1.0f
            binding.btnCopy.isEnabled = false
            binding.btnEditCode.isEnabled = true
        } else {
            binding.btnCopy.alpha = 1.0f
            binding.btnEditCode.alpha = 0.0f
            binding.btnCopy.isEnabled = true
            binding.btnEditCode.isEnabled = false
        }
    }

    private fun setTextCode() {
        val baseCode = viewModel.userProfile().invitationBase
        binding.edCode.setText(baseCode)
    }


     fun setupClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnEditCode.setOnClickListener {
            findNavController().navigate(R.id.action_addSupporterFragment_to_createNewInvitationCodeFragment)
        }

        binding.btnCopy.setOnClickListener {
            copyTextToClipboard(viewModel.userProfile().invitationBase!!)
        }

    }





}