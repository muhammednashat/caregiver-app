package mnshat.dev.myproject.features.chatting

import android.annotation.SuppressLint
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
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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


    fun getMessages(chatId: String) {
        viewModelScope.launch {
            retrieveMessagesFlow(chatId).collect { messages ->
                _messagesFlow.value = messages
            }
        }
    }

    private fun retrieveMessagesFlow(chatId: String): Flow<MutableList<Message>> = callbackFlow {

        val listenerRegistration: ListenerRegistration = db.collection(CHATS).document(chatId)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                   log("11111111111112")
                    val messages = documentSnapshot.toObject(Messages::class.java)?.messages
                        ?: mutableListOf()
                        trySend(messages)
                } else {

                    trySend(mutableListOf())
                }
            }
        awaitClose { listenerRegistration.remove() }
    }

    fun sendMessage(newMessage:Message, chatId: String) {
        viewModelScope.launch {
            val currentMessages = _messagesFlow.value
            currentMessages.add(newMessage)

            db.collection(CHATS).document(chatId).set(Messages(currentMessages))
                .addOnSuccessListener {

                    log("Message sent successfully")
                }
                .addOnFailureListener {
                    log("Failed to send message: ${it.message}")
                }
        }
    }

}
