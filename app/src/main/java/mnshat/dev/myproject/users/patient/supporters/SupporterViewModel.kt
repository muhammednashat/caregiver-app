package mnshat.dev.myproject.users.patient.supporters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.users.patient.supporters.data.SupportersRepo
import mnshat.dev.myproject.util.log
import javax.inject.Inject


@HiltViewModel
class SupporterViewModel @Inject constructor(
    private val supportersRepo: SupportersRepo,
      )
    : ViewModel() {

   val userProfile = supportersRepo.userProfile()



    fun retrieveSupporters(){
        viewModelScope.launch {
            log("retrieveSupporters 1 ")
            try {
                val supporters =
                    supportersRepo.retrieveSupporters(userProfile.id!!)
            }catch (e:Exception){
                log("Exception")
               log(e.message.toString())
            }
        }
    }


}