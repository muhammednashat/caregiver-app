package mnshat.dev.myproject.auth

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentLoginBinding
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.EMAIL
import mnshat.dev.myproject.util.GENDER
import mnshat.dev.myproject.util.PASSWORD

class LoginFragment : AuthBaseFragment<FragmentLoginBinding>() {

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
        binding.logIn.setOnClickListener {
            if (_viewModel.validToLogin(requireContext())){
                showProgressDialog()
                login{
                    if (it){
                        updateRegistrationInfoLocally()
                    }
                }
            }else{
                showToast(_viewModel.errorMessage!!)
            }
        }

    }

    private fun updateRegistrationInfoLocally(){
        if(isRememberMe){
                sharedPreferences.storeString(EMAIL ,_viewModel.email.value)
                sharedPreferences.storeString(PASSWORD , _viewModel.password.value)
        }else{
            sharedPreferences.storeString(EMAIL ,   "")
            sharedPreferences.storeString(PASSWORD ,   "")
        }
    }
    private fun setUpSwitch() {
        val email = sharedPreferences.getString(EMAIL)
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