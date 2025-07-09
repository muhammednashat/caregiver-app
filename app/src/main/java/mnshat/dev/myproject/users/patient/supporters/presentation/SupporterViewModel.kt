package mnshat.dev.myproject.users.patient.supporters.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.users.patient.supporters.data.repos.SupportersRepo
import mnshat.dev.myproject.util.USER_PROFILE
import mnshat.dev.myproject.util.log
import javax.inject.Inject


@HiltViewModel
class SupporterViewModel @Inject constructor(
    private val supportersRepo: SupportersRepo,
      )
    : ViewModel() {

   private val _status = MutableLiveData<String?>()
   var status: LiveData<String?> = _status

    val supportersProfile = supportersRepo.supportersProfile


   val userProfile = supportersRepo.userProfile()



    fun retrieveSupporters(){
        viewModelScope.launch {
            try {
                    supportersRepo.retrieveSupportersIds(userProfile.id!!)
            }catch (e:Exception){
                log("Exception")
               log(e.message.toString())
            }
        }
    }

    fun storeNewInvitationCode(newInvitationCode: String) {
        viewModelScope.launch {
            try {
            supportersRepo.storeNewInvitationCode(newInvitationCode)
            updateUserProfileLocal(newInvitationCode)
            } catch (e: Exception) {
                _status.value = e.message
            }
        }
    }

    private fun updateUserProfileLocal(newInvitationCode: String) {
        try {
            supportersRepo.updateUserProfileLocal(newInvitationCode)
            _status.value = ""

        }catch (e:Exception){
            _status.value = e.message
        }

    supportersRepo.sharedPreferences.storeObject(USER_PROFILE, userProfile)

    }


}