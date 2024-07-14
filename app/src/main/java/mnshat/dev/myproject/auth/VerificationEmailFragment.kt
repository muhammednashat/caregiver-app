package mnshat.dev.myproject.auth

//import mnshat.dev.myproject.model.RegistrationUtil
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentVerificationEmailBinding
import mnshat.dev.myproject.util.ENGLISH_KEY


class VerificationEmailFragment : AuthBaseFragment<FragmentVerificationEmailBinding>() {
    override fun getLayout(): Int = R.layout.fragment_verification_email
    override fun setupClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnNext.setOnClickListener {
//            if (RegistrationUtil.isEmailValid(requireContext())) {
//                showProgressDialog()
////                retrieveUser()
//            } else {
//                dismissProgressDialog()
//                showToast(RegistrationUtil.errorMessage!!)
//            }
        }
        binding.contactUs.setOnClickListener {
            findNavController().navigate(R.id.action_verificationEmailFragment_to_contactUsFragment)
        }
    }
//    private fun retrieveUser(){
//        retrieveUser(RegistrationUtil.email) {
//            if (it != null) {
//                resetUserPassword()
//            } else {
//                dismissProgressDialog()
//                showToast( getString(R.string.user_not_found))
//            }
//        }
//    }

    private fun resetUserPassword(){
//        resetUserPassword(RegistrationUtil.email!!){
//         if (it){
//             findNavController().popBackStack()
//             showToast(getString(R.string.reset_password_success))
//         }else{
//             showToast( getString(R.string.reset_password_failure))
//         }
//            dismissProgressDialog()
//        }
    }
    override fun initializeViews() {

        if (currentLang != ENGLISH_KEY) {
            Log.e("TAG" , "fsdfd")
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }

    }


    override fun onStart() {
        super.onStart()
//        RegistrationUtil.email = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel
    }



}