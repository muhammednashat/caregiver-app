package mnshat.dev.myproject.auth.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.AuthBaseFragment
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.databinding.FragmentVerificationEmailBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.LANGUAGE


@AndroidEntryPoint
class VerificationEmailFragment : BaseFragment() {

private lateinit var binding : FragmentVerificationEmailBinding
private val baseViewModel : BaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVerificationEmailBinding.inflate(inflater, container, false)
        setupClickListener()
        initializeViews()
        return binding.root
    }

     fun setupClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnNext.setOnClickListener {
            if(isEmailValid(binding.editEmail.text.toString())){
                showProgressDialog()
//                retrieveUser()
            }else{

            }
        }
        binding.contactUs.setOnClickListener {
            findNavController().navigate(R.id.action_verificationEmailFragment_to_contactUsFragment)
        }
    }

    private fun isEmailValid(_email:String): Boolean {
        var email = _email
        return if (email.isEmpty()) {
            showToast(getString(R.string.enter_email))
            false
        } else {
            email = email!!.trim()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (!email!!.matches(emailPattern.toRegex())) {
                showToast(getString(R.string.email_error))
                false
            } else
                true
        }
    }
    private fun retrieveUser(){
//        FirebaseService.retrieveUser("") {
//            if (it != null) {
//                resetUserPassword()
//            } else {
//                dismissProgressDialog()
//                showToast( getString(R.string.user_not_found))
//            }
//        }
    }

    private fun resetUserPassword(){
        FirebaseService.resetUserPassword(""){
         if (it){
             findNavController().popBackStack()
             showToast(getString(R.string.reset_password_success))
         }else{
             showToast( getString(R.string.reset_password_failure))
         }
            dismissProgressDialog()
        }
    }
     fun initializeViews() {

        if (baseViewModel.sharedPreferencesManager.getString(LANGUAGE) != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }

    }





}