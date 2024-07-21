package mnshat.dev.myproject.auth

import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.inappmessaging.dagger.Provides
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.factories.AuthViewModelFactory
import mnshat.dev.myproject.factories.PatientViewModelFactory
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.users.caregiver.CaregiverScreenActivity
import mnshat.dev.myproject.users.patient.intro.IntroActivity
import mnshat.dev.myproject.users.patient.main.PatientViewModel
import mnshat.dev.myproject.users.patient.main.UserScreensActivity
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.CODE_USED
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.INVITATION_CODE
import mnshat.dev.myproject.util.IS_SECOND_TIME
import mnshat.dev.myproject.util.IS_SIGNING
import mnshat.dev.myproject.util.NUMBER_SUPPORTERS
import mnshat.dev.myproject.util.SUPPORTERS
import mnshat.dev.myproject.util.USER
import javax.inject.Inject

open abstract class AuthBaseFragment<T : ViewDataBinding> : BaseFragment<T>() {

    lateinit var _viewModel: AuthViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val factory = AuthViewModelFactory(sharedPreferences,activity?.application!!)
        _viewModel = ViewModelProvider(requireActivity(), factory)[AuthViewModel::class.java]
        super.onActivityCreated(savedInstanceState)
    }



    fun login(callBack: (Boolean) -> Unit) {
        FirebaseService.login(_viewModel.email.value?.trim()!!, _viewModel.password.value?.trim()!!) {
            if (it == null) {
                callBack(true)
                FirebaseService.retrieveCurrentTasksForUser(_viewModel.email.value?.trim()!!){
                   it?.let {
                       _viewModel.storeCurrentTaskLocally(it,sharedPreferences){}
                   }
                    determineTheUserTONavigate(_viewModel.email.value?.trim()!!)
                }
            } else {
                callBack(false)
                dismissProgressDialog()
                showToast(it)
            }
        }
    }

    fun signUp(registrationData: RegistrationData?) {
        FirebaseService.signUp(_viewModel.email.value!!.trim(), _viewModel.password.value!!.trim()) {
            isSigned , userId ->
            if (isSigned) {
                if (_viewModel.typeOfUser.value == USER){
                    _viewModel.invitationCode.value = userId?.take(8)
                }
                _viewModel.id =userId
                _viewModel.retrieveTaskDayFromDatabase("1",_viewModel.email.value?.trim()!!,userId!!,sharedPreferences){

                    saveUserAdditionalInfo(registrationData) // 8

                }
            } else {
                dismissProgressDialog()
                showToast(userId!!)
            }
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()
        _viewModel.name.value?.let {
            _viewModel.invitationCode.observe(viewLifecycleOwner) {
        }
        }
    }

    private fun determineTheUserTONavigate(email: String?) {
        FirebaseService.retrieveUser(null,email) { user ->
            if (user != null) {
                user.storeDataLocally(sharedPreferences)
                navigateToActivity(user.typeOfUser)
            }
        }
    }

    private fun saveUserAdditionalInfo(registrationData: RegistrationData?) {
        FirebaseService.getToken { str ->
           _viewModel.token.value =   str
            _viewModel.storeDataLocally(sharedPreferences)
            sharedPreferences.storeBoolean(IS_SIGNING, true)
            navigateToActivity(  _viewModel.typeOfUser.value)

            if (_viewModel.typeOfUser.value == CAREGIVER) {
                addSuppToUser(registrationData) {
                    FirebaseService.saveUserAdditionalInfo(_viewModel.getRegistrationDataForCaregiver())
                }
            } else {
                FirebaseService.saveUserAdditionalInfo(_viewModel.getRegistrationDataForUser())
            }
        }
    }

    private fun addSuppToUser(registrationData: RegistrationData?, callBack: (Boolean) -> Unit) {
        var list = mutableListOf<String>()
        if (registrationData?.numberSupporters != 0) {
            list = registrationData?.supports!!.toMutableList()
        }
        list.add(_viewModel.email.value!!)
        val updateData = mutableMapOf(
            SUPPORTERS to list,
            HAS_PARTNER to true,
            NUMBER_SUPPORTERS to registrationData.numberSupporters!! + 1,
            INVITATION_CODE to "null" ,
            CODE_USED to true,
        )

        FirebaseService.updateItemsProfileUser(registrationData?.id!!, updateData) {
            if (it)
                callBack(true)
            else
                callBack(true)
        }

    }


    private fun navigateToActivity(typeOfUser: String?) {
        when (typeOfUser) {
            USER -> {
                if (sharedPreferences.getBoolean(IS_SIGNING)) {
                    if (sharedPreferences.getBoolean(IS_SECOND_TIME)) {
                        startActivity(Intent(requireActivity(), UserScreensActivity::class.java))
                    }
                    else {
                        startActivity(Intent(requireActivity(), IntroActivity::class.java))
                        sharedPreferences.storeBoolean(IS_SECOND_TIME, true)
                    }
                    sharedPreferences.storeBoolean(IS_SIGNING, false)
                }
                else {
                    startActivity(Intent(requireActivity(), UserScreensActivity::class.java))
                }
            }

            CAREGIVER -> {
                startActivity(Intent(requireActivity(), CaregiverScreenActivity::class.java))
            }
        }
        dismissProgressDialog()
        requireActivity().finish()
    }




}
