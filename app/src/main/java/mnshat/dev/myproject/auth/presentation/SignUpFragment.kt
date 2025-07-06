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
import mnshat.dev.myproject.databinding.FragmentSignUpBinding
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.users.caregiver.main.CaregiverScreenActivity
import mnshat.dev.myproject.users.patient.main.presentaion.UserScreensActivity
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.USER
import mnshat.dev.myproject.util.log

@AndroidEntryPoint

class SignUpFragment : BaseFragment(){

    private  lateinit var binding: FragmentSignUpBinding

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        setupClickListener()
        return  binding.root
    }




    fun setupClickListener() {
        binding.chooseUser.user.setOnClickListener {
            viewModel.typeOfUser.value = USER
        }
        binding.chooseUser.caregiver.setOnClickListener {
            viewModel.typeOfUser.value = CAREGIVER
        }
        binding.logIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            viewModel.clearData()
        }
        binding.contactUs.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_contactUsFragment)
        }
        binding.age.setOnClickListener {
            AgeFragment().show(childFragmentManager, AgeFragment::class.java.name)
        }
        binding.gender.setOnClickListener {
            GenderFragment().show(childFragmentManager, GenderFragment::class.java.name)
        }

        binding.btnSign.setOnClickListener {
            if (viewModel.validToRegisterUser(requireActivity())){
                signUp()
            }else{
                showToast(viewModel.errorMessage!!)

            }
        }
    }


    private fun signUp(){
        if (isConnected()){
            showProgressDialog()
            viewModel.signUp(requireActivity())
        }else{
            showNoInternetSnackBar(binding.root)
        }
    }


    private fun changeUserUi(type: String) {

        if (type == USER) {
            binding.chooseUser.user.setBackgroundResource(R.drawable.corner_four_dark_blue)
            binding.chooseUser.patientText.setTextColor(resources.getColor(R.color.white))
            binding.chooseUser.caregiver.setBackgroundResource(R.drawable.corner_four_gray)
            binding.chooseUser.careText.setTextColor(resources.getColor(R.color.dark_gray))
            binding.chooseUser.patientStatusCircle.visibility = View.GONE
            binding.chooseUser.patientStatusChecked.visibility = View.VISIBLE
            binding.chooseUser.careStatusChecked.visibility = View.GONE
            binding.chooseUser.careStatusCircle.visibility = View.VISIBLE
        } else {
            binding.chooseUser.caregiver.setBackgroundResource(R.drawable.corner_four_dark_blue)
            binding.chooseUser.careText.setTextColor(resources.getColor(R.color.white))
            binding.chooseUser.user.setBackgroundResource(R.drawable.corner_four_gray)
            binding.chooseUser.patientText.setTextColor(resources.getColor(R.color.dark_gray))
            binding.chooseUser.careStatusCircle.visibility = View.GONE
            binding.chooseUser.careStatusChecked.visibility = View.VISIBLE
            binding.chooseUser.patientStatusChecked.visibility = View.GONE
            binding.chooseUser.patientStatusCircle.visibility = View.VISIBLE
        }

    }

    private fun hideContentUser(needHide: Boolean) {

        if (needHide){
            binding.additionalContent.visibility = View.VISIBLE
            binding.invitationCode.visibility = View.GONE
        }
        else{
            binding.invitationCode.visibility = View.VISIBLE
            binding.additionalContent.visibility = View.INVISIBLE
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.strGender.value = getString(R.string.gender)
        viewModel.strAge.value = getString(R.string.age_group)




        viewModel.authStatus.observe(viewLifecycleOwner){

            dismissProgressDialog()
            it?.let {
                if (it.isNotEmpty()){
                    showToast(it) // error message
                }else{
                    viewModel.updateAuthStatusLocale()
                    showToast(getString(R.string.welcome))
                    navigateBasedUserType()
                }
            }
        }

        viewModel.typeOfUser.observe(viewLifecycleOwner) {
            it?.let {
                hideContentUser(it == USER)
                changeUserUi(it)
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


    override fun onStop() {
        super.onStop()
        viewModel.clearData()
    }





}