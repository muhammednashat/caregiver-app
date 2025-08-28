package mnshat.dev.myproject.users.patient.profile.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.firebase.FirebaseService.userEmail
import mnshat.dev.myproject.users.patient.dailyprogram.data.DailyProgramRepository
import mnshat.dev.myproject.users.patient.profile.data.ProfileRepo
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val sharedPreferences: SharedPreferencesManager,
    private val profileRepo: ProfileRepo,
    private val dailyProgramRepo: DailyProgramRepository,

) : ViewModel() {


    fun userProfile() = profileRepo.userProfile()
     private  val _status = MutableLiveData<Boolean?>()
     val status: LiveData<Boolean?>  = _status

    fun restStatus(){
        _status.value = null
    }


    fun uploadImageToFireStorage(uri: Uri) {
        viewModelScope.launch {
            try {
                profileRepo.uploadImageToFireStorage(uri)
                _status.value = true
            } catch (e: Exception) {
              _status.value = false
            }
        }


    }


    fun updateUserProfileRemotely(key: String ,value: Any) {
        viewModelScope.launch {
            try {
                profileRepo.updateUserProfileRemotely(key, value)
                _status.value = true
            } catch (e: Exception) {
              _status.value = false
            }
        }
    }

    fun changeUserPassword(currentPassword: String, newPassword: String) {
         val fireAuth = FirebaseAuth.getInstance()
         val currentUser = fireAuth.currentUser
         val credential = EmailAuthProvider.getCredential(userEmail!!, currentPassword)
         viewModelScope.launch {
           try {
             currentUser?.reauthenticate(credential)?.addOnCompleteListener { task ->
                  if (task.isSuccessful) {
                      currentUser.updatePassword(newPassword).addOnCompleteListener { task ->
                         _status.value = task.isSuccessful

                      }
                  } else {
                      log(task.exception.toString() + "   ele")
                      _status.value = false
                  }
              }?.await()
           }
           catch (e:Exception){
               _status.value = false
           }
       }

    }

    fun updateReligion(key: String, value: Any) {
        viewModelScope.launch {
            try {
                profileRepo.updateUserProfileRemotely(key, value)
                log("4")
                resetCurrentDay()
                log("7")
                _status.value = true
            } catch (e: Exception) {
                _status.value = false
            }
        }

    }
   private suspend fun resetCurrentDay() {
        log("5")
       val userProfile = userProfile()
       log(userProfile.religion!!.toString())
       dailyProgramRepo.getNextDay(userProfile.currentDay!!)
       log("6")

    }



}