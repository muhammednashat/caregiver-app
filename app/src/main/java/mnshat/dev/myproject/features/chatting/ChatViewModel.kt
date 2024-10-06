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
import mnshat.dev.myproject.model.MetaDataMessages
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.CHATS
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.USER_ID
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

    private val _clearEditText = MutableLiveData<Boolean>()
    val clearEditText:LiveData<Boolean> = _clearEditText

    private val _messages = MutableLiveData<List<Message>>()
    val messages:LiveData<List<Message>> = _messages




    fun getMessages(chatId: String) {
        log("getMessages")
        viewModelScope.launch {
            retrieveMessages(chatId)
        }
    }

    fun getMessagesList(chatId: String) {
        log(" chatId  = $chatId")

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
                        val messages = document.toObject(Messages::class.java)
                        messagesList.add(messages!!)
                    }
                    _messagesList.value = messagesList
                } else {
                    _messagesList.value = mutableListOf()
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
                    _clearEditText.value = true
                } else {

                }
            }
    }

    fun sendMessage(newMessage:Message,metaDataMessages: MetaDataMessages,chatId: String) {

            val currentMessages = _messages.value?.toMutableList()

            currentMessages?.add(newMessage)

            db.collection(CHATS).document(chatId).set(Messages(
                metaDataMessages,
                currentMessages
            ))
                .addOnSuccessListener {
                    log("Message sent successfully")
                }
                .addOnFailureListener {
                    log("Failed to send message: ${it.message}")
                }

    }

    fun resetClearEditText(){
        _clearEditText.value = false
    }

}
