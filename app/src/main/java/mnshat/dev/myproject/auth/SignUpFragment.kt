package mnshat.dev.myproject.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentSignUpBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.USER
import mnshat.dev.myproject.util.log

class SignUpFragment : AuthBaseFragment<FragmentSignUpBinding>(){
    override fun initializeViews() {
    }

    override fun getLayout() = R.layout.fragment_sign_up
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = _viewModel
        observeViewModel()
    }

    private fun observeViewModel() {
        _viewModel.strGender.value = getString(R.string.gender)
        _viewModel.strAge.value = getString(R.string.age_group)
        _viewModel.typeOfUser.observe(viewLifecycleOwner) {
            it?.let {
                hideContentUser(it == USER)
                changeUserUi(it)
            }
        }
    }


     override fun setupClickListener() {
        binding.chooseUser.user.setOnClickListener {
            _viewModel.typeOfUser.value = USER
        }
        binding.chooseUser.caregiver.setOnClickListener {
            _viewModel.typeOfUser.value = CAREGIVER
        }
        binding.logIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                _viewModel.clearData()
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
            if (_viewModel.validToRegisterUser(requireContext())) {
                if (_viewModel.typeOfUser.value == CAREGIVER){
                    retrieveUser()
                }
                else{
                    showProgressDialog()
                    signUp(null)

                }
            }else showToast(_viewModel.errorMessage!!)
        }
    }

private fun retrieveUser(){
     showProgressDialog()
    log(_viewModel.invitationCode.value!!)
    FirebaseService.retrieveUser(_viewModel.typeOfUser.value,_viewModel.invitationCode.value?.trim()){
        if (it == null){
            dismissProgressDialog()
            showToast(getString(R.string.enter_valid_invitation))
        }else{
            setPartner(it)
        }
    }
}

    private fun setPartner(it: RegistrationData) {
        _viewModel.namePartner = it.name
        _viewModel.idPartner = it.id
        _viewModel.emailPartner = it.email
        signUp(it)
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


}