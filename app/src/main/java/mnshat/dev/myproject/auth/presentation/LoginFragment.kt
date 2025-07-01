package mnshat.dev.myproject.auth.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentLoginBinding
import mnshat.dev.myproject.users.caregiver.main.CaregiverScreenActivity
import mnshat.dev.myproject.users.patient.main.presentaion.UserScreensActivity
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.USER_EMAIL
import mnshat.dev.myproject.util.GENDER
import mnshat.dev.myproject.util.PASSWORD
import mnshat.dev.myproject.util.log

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

              login()

//              if (viewModel.validToLogin(requireActivity())){
//                  login()
//              }else{
//                  showToast(viewModel.errorMessage!!)
//              }
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

     fun updateRegistrationInfoLocally(){
        if(isRememberMe){
                viewModel.sharedPreferences.storeString(USER_EMAIL ,viewModel.email.value)
                viewModel.sharedPreferences.storeString(PASSWORD , viewModel.password.value)
        }else{
            viewModel.sharedPreferences.storeString(USER_EMAIL ,   "")
            viewModel.sharedPreferences.storeString(PASSWORD ,   "")
        }
    }

    fun setUpSwitch() {
        val email = viewModel.sharedPreferences.getString(USER_EMAIL)
        val pass = viewModel.sharedPreferences.getString(PASSWORD)
        if (email != ""){
            isRememberMe = true
            binding.switch1.isChecked = true
            viewModel.email.value = email
            viewModel.password.value = pass
        }
        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            isRememberMe = isChecked
        }
    }

    override fun onStart() {
        super.onStart()
        setUpSwitch()
        viewModel.sharedPreferences.storeInt(GENDER,0)
        viewModel.sharedPreferences.storeInt(AGE_GROUP,0)
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

                log("observeViewModel() called with: it = $it")
                dismissProgressDialog()

                if (it.isNotEmpty()){
                    showToast(it)
                }else{
//                viewModel.updateAuthStatusLocale()
                    showToast(getString(R.string.welcome))
//                navigateBasedUserType()
                }
                viewModel.resetAuthStatus()

            }

        }
    }

    private fun navigateBasedUserType() {
        if (viewModel.typeOfUser.value == CAREGIVER){
            startActivity(Intent(requireContext(), CaregiverScreenActivity::class.java))
        }else{
            startActivity(Intent(requireContext(), UserScreensActivity::class.java))
        }
    }

}