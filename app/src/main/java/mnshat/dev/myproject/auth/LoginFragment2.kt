package mnshat.dev.myproject.auth

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentLoginBinding
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.USER_EMAIL
import mnshat.dev.myproject.util.GENDER
import mnshat.dev.myproject.util.PASSWORD

class LoginFragment2 : AuthBaseFragment<FragmentLoginBinding>() {

    private var isRememberMe = false

    override fun getLayout() = R.layout.fragment_login
    override fun initializeViews() { }


    override  fun setupClickListener(){
        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            _viewModel.clearData()

        }
        binding.forgetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_verificationEmailFragment)
        }
        binding.contactUs.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_contactUsFragment)
        }
        binding.logIn.setOnClickListener { view ->

            if (_viewModel.validToLogin(requireContext())){
             if(_viewModel.isConnected()){
                 showProgressDialog()
                 login{
                     if (it){
                         updateRegistrationInfoLocally()
                     }
                 }
             }else{
                 showNoInternetSnackBar(view)
             }

            }


            else{
                showToast(_viewModel.errorMessage!!)
            }

        }

    }

    private fun updateRegistrationInfoLocally(){
        if(isRememberMe){
                sharedPreferences.storeString(USER_EMAIL ,_viewModel.email.value)
                sharedPreferences.storeString(PASSWORD , _viewModel.password.value)
        }else{
            sharedPreferences.storeString(USER_EMAIL ,   "")
            sharedPreferences.storeString(PASSWORD ,   "")
        }
    }
    private fun setUpSwitch() {
        val email = sharedPreferences.getString(USER_EMAIL)
        val pass = sharedPreferences.getString(PASSWORD)
        if (email != ""){
            isRememberMe = true
            binding.switch1.isChecked = true
            _viewModel.email.value = email
            _viewModel.password.value = pass
        }
        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            isRememberMe = isChecked
        }
    }

    override fun onStart() {
        super.onStart()
        setUpSwitch()
        sharedPreferences.storeInt(GENDER,0)
        sharedPreferences.storeInt(AGE_GROUP,0)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel
    }

}