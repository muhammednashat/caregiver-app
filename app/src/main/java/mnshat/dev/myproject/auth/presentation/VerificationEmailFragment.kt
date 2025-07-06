package mnshat.dev.myproject.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentVerificationEmailBinding


@AndroidEntryPoint
class VerificationEmailFragment : BaseFragment() {

private lateinit var binding : FragmentVerificationEmailBinding
private val viewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVerificationEmailBinding.inflate(inflater, container, false)
        setupClickListener()
        observeViewModel()
        return binding.root
    }

     fun setupClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnNext.setOnClickListener {
            viewModel.email.value = binding.editEmail.text.toString()
            if(viewModel.isEmailValid(requireActivity())){
                tryRetrieveUser()
            }else{
                showToast(viewModel.errorMessage!!)
            }
        }
        binding.contactUs.setOnClickListener {
            findNavController().navigate(R.id.action_verificationEmailFragment_to_contactUsFragment)
        }
    }

    private fun tryRetrieveUser(){

        if (isConnected()){
            showProgressDialog()
            viewModel.tryRetrieveUser(viewModel.email.value!!, requireActivity())
        }else{
            showNoInternetSnackBar(binding.root)
        }
    }


    private fun observeViewModel() {
        viewModel.authStatus.observe(viewLifecycleOwner){
            it?.let {
                dismissProgressDialog()
                if (it.isNotEmpty()){
                    showToast(it)
                }else{
                    showToast(getString(R.string.password_reset_email_sent))
                    findNavController().popBackStack()
                }
                viewModel.resetAuthStatus()
            }

        }
    }





}