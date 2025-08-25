package mnshat.dev.myproject.chatting.presintation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.chatting.data.ChattingRepo
import mnshat.dev.myproject.chatting.entity.Message
import mnshat.dev.myproject.chatting.entity.MetaDataMessages
import mnshat.dev.myproject.users.patient.supporters.data.repos.SupportersRepo
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    val chattingRepo: ChattingRepo,
    val sharedPreferences: SharedPreferencesManager,
     val supportersRepo: SupportersRepo,

) : ViewModel() {


    val chattingList = chattingRepo.chattingList
    val messages = chattingRepo.messagesList
    val partners = supportersRepo.supportersProfile
    val user = chattingRepo.user
    private val _clearEditText = MutableLiveData<Boolean>()
    val clearEditText:LiveData<Boolean> = _clearEditText
    var partnerId = ""

    init {
        chattingRepo.retrieveChattingList()
    }

    fun isUserCaregiver() = chattingRepo.isUserCaregiver()
    fun hasPartner() = chattingRepo.hasPartner()!!

    fun listenToMessages() {
        viewModelScope.launch {
            try {
                chattingRepo.listenToMessages(partnerId)
            }catch (e:Exception){

            }
        }
    }

    fun retrievePartners(){
        try {
            viewModelScope.launch {
                supportersRepo.retrievePartnersIds(supportersRepo.userProfile().id!!)
            }
        }catch (e:Exception){

        }
    }


fun sendMessage(message: Message,metaDataMessages: MetaDataMessages){
    viewModelScope.launch {
        try {
            chattingRepo.sendMessage(message,metaDataMessages, partnerId)
            _clearEditText.value = true
        }catch (e:Exception){

        }
}
}



    fun resetClearEditText(){
        _clearEditText.value = false
    }

    fun clearMessages() {
        messages.value = null
    }

}
