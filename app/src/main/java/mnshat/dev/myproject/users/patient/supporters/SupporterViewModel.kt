package mnshat.dev.myproject.users.patient.supporters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.auth.data.entity.UserProfile
import mnshat.dev.myproject.users.patient.supporters.data.SupportersRepo
import mnshat.dev.myproject.util.log
import javax.inject.Inject


@HiltViewModel
class SupporterViewModel @Inject constructor(
    private val supportersRepo: SupportersRepo,
      )
    : ViewModel() {

    val supportersProfile = supportersRepo.supportersProfile


   val userProfile = supportersRepo.userProfile()



    fun retrieveSupporters(){
        viewModelScope.launch {
            try {
                val supporters =
                    supportersRepo.retrieveSupportersIds(userProfile.id!!)


            }catch (e:Exception){
                log("Exception")
               log(e.message.toString())
            }
        }
    }


}