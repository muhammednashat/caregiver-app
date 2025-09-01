package mnshat.dev.myproject.auth.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentLoginBinding
import mnshat.dev.myproject.users.caregiver.main.CaregiverScreenActivity
import mnshat.dev.myproject.users.patient.main.presentaion.UserScreensActivity
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.USER_EMAIL

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private var isRememberMe = false
    private lateinit var binding: FragmentLoginBinding
    private  val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        setupClickListener()
        return  binding.root
    }


  private    fun setupClickListener(){
        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            viewModel.clearData()

        }
        binding.forgetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_verificationEmailFragment)
        }
        binding.contactUs.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_contactUsFragment)
        }

          binding.logIn.setOnClickListener {
              if (viewModel.validToLogin(requireActivity())){
                  login()
              }else{
                  showToast(viewModel.errorMessage!!)
              }
          }

    }

    private fun login(){

        if (isConnected()){
            showProgressDialog()
            viewModel.login()
        }else{
            showNoInternetSnackBar(binding.root)
    }
    }

     private fun updateRegistrationInfoLocally(){
        if(isRememberMe){
                viewModel.sharedPreferences.storeString(USER_EMAIL ,viewModel.email.value)
        }else{
            viewModel.sharedPreferences.storeString(USER_EMAIL ,   "")
        }
    }

    private fun setUpSwitch() {
        val email = viewModel.sharedPreferences.getString(USER_EMAIL)
        if (email != ""){
            isRememberMe = true
            binding.switch1.isChecked = true
            viewModel.email.value = email
        }
        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            isRememberMe = isChecked
        }
    }

    override fun onStart() {
        super.onStart()
        setUpSwitch()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.authStatus.observe(viewLifecycleOwner){
            it?.let {
                dismissProgressDialog()
                if (it.isNotEmpty()){
                    showToast(it)
                }else{
                viewModel.updateAuthStatusLocale(false)
                    updateRegistrationInfoLocally()
                    showToast(getString(R.string.welcome))
                  navigateBasedUserType()
                }
                viewModel.resetAuthStatus()

            }

        }
    }

    private fun navigateBasedUserType() {
        val userType = viewModel.currentUserProfile().typeOfUser

        if (userType == CAREGIVER){
            startActivity(Intent(requireContext(), CaregiverScreenActivity::class.java))
        }else{
            startActivity(Intent(requireContext(), UserScreensActivity::class.java))
        }
    }

}