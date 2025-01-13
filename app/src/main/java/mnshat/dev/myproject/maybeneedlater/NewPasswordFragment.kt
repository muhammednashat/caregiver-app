package mnshat.dev.myproject.maybeneedlater

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.AuthBaseFragment
import mnshat.dev.myproject.databinding.FragmentNewPasswordBinding

class NewPasswordFragment  : AuthBaseFragment<FragmentNewPasswordBinding>() {

    override fun setupClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.send.setOnClickListener {
            findNavController().navigate(R.id.action_verificationEmailFragment_to_contactUsFragment)
        }
    }

    override fun initializeViews() {
    }

    override fun getLayout(): Int {
        return  R.layout.fragment_new_password
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel
    }



}