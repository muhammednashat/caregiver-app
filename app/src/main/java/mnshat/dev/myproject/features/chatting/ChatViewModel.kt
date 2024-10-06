package mnshat.dev.myproject.features.chatting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.model.Message
import mnshat.dev.myproject.model.Messages
import mnshat.dev.myproject.util.CHATS
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log

class ChatViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application

) : BaseViewModel(
    sharedPreferences,
    application
) {


    private var db = Firebase.firestore

    private val _messagesList = MutableLiveData<List<Messages>>()
    val messagesList:LiveData<List<Messages>> = _messagesList

    private val _messages = MutableLiveData<List<Message>>()
    val messages:LiveData<List<Message>> = _messages




    fun getMessages(chatId: String) {
        log("getMessages")
        viewModelScope.launch {
            retrieveMessages(chatId)
        }
    }

    fun getMessagesList(chatId: String) {
        viewModelScope.launch {
            retrieveMessagesList(chatId)
            }
        }

    private fun retrieveMessagesList(chatIdPrefix: String){
         db.collection(CHATS)
            .orderBy("__name__")
            .startAt(chatIdPrefix)
            .endAt(chatIdPrefix + '\uf8ff')
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    val messagesList = mutableListOf<Messages>()

                    for (document in querySnapshot.documents) {
                        log("for 1232 ")

                        val messages = document.toObject(Messages::class.java)
                        messagesList.add(messages!!)
                    }
                    log("trySend 1232 ")

//                    trySend(messagesList)
                } else {
//                    trySend(mutableListOf())
                }
            }

    }


    private fun retrieveMessages(chatId: String){

           db.collection(CHATS).document(chatId)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val messages =
                        documentSnapshot.toObject(Messages::class.java)?.messages ?: mutableListOf()
                    _messages.value = messages
                } else {
                    _messages.value = mutableListOf()
                }
            }
    }

    fun sendMessage(newMessage:Message,namePartner:String,urlImage:String ,idPartner:String,chatId: String) {

//
//            val currentMessages = _messagesList.value
//
//            currentMessages.add(newMessage)
//
//            db.collection(CHATS).document(chatId).set(Messages(namePartner,idPartner,urlImage,currentMessages))
//                .addOnSuccessListener {
//
//                    log("Message sent successfully")
//                }
//                .addOnFailureListener {
//                    log("Failed to send message: ${it.message}")
//                }

    }

}
}
