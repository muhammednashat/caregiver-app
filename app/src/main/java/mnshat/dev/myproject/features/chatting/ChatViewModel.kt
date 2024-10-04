package mnshat.dev.myproject.features.chatting

import android.app.Application
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
    private val _messagesFlow = MutableStateFlow<MutableList<Message>>(mutableListOf())
    val messagesFlow = _messagesFlow.asStateFlow()

    private val _messagesListFlow = MutableStateFlow<MutableList<Messages>>(mutableListOf())
    val messagesListFlow = _messagesListFlow.asStateFlow()


    fun getMessages(chatId: String) {
        viewModelScope.launch {
            retrieveMessagesFlow(chatId).collect { messages ->
                _messagesFlow.value = messages
            }
        }
    }

    fun getMessagesList(chatId: String) {
        viewModelScope.launch {
            retrieveMessagesListFlow(chatId).collect { messagesList ->
                _messagesListFlow.value = messagesList
            }
        }
    }

    private fun retrieveMessagesListFlow(chatIdPrefix: String): Flow<MutableList<Messages>> = callbackFlow {
        log("if retrieveMessagesListFlow ")
        val listenerRegistration: ListenerRegistration = db.collection(CHATS)
            .orderBy("__name__")
            .startAt(chatIdPrefix)
            .endAt(chatIdPrefix + '\uf8ff')
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    val messagesList = mutableListOf<Messages>()
                    log("if 1232 ")

                    for (document in querySnapshot.documents) {
                        log("for 1232 ")

                        val messages = document.toObject(Messages::class.java)
                        messagesList.add(messages!!)
                    }
                    log("trySend 1232 ")

                    trySend(messagesList)
                } else {
                    trySend(mutableListOf())
                }
            }

        awaitClose { listenerRegistration.remove() }
    }


    private fun retrieveMessagesFlow(chatId: String): Flow<MutableList<Message>> = callbackFlow {

        val listenerRegistration: ListenerRegistration = db.collection(CHATS).document(chatId)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val messages =
                        documentSnapshot.toObject(Messages::class.java)?.messages ?: mutableListOf()
                        trySend(messages)
                } else {

                    trySend(mutableListOf())
                }
            }
        awaitClose { listenerRegistration.remove() }
    }

    fun sendMessage(newMessage:Message,namePartner:String,urlImage:String ,idPartner:String,chatId: String) {
        viewModelScope.launch {
            val currentMessages = _messagesFlow.value
            currentMessages.add(newMessage)

            db.collection(CHATS).document(chatId).set(Messages(namePartner,idPartner,urlImage,currentMessages))
                .addOnSuccessListener {

                    log("Message sent successfully")
                }
                .addOnFailureListener {
                    log("Failed to send message: ${it.message}")
                }
        }
    }

}
