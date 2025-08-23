package mnshat.dev.myproject.chatting.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import mnshat.dev.myproject.chatting.entity.Chatting
import mnshat.dev.myproject.chatting.entity.Message
import mnshat.dev.myproject.chatting.entity.MetaDataMessages
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.CHATS
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log
import kotlin.jvm.java

class ChattingRepo(
    private val firestore: FirebaseFirestore,
    private val sharedPreferences: SharedPreferencesManager,
    ) {

    val user = sharedPreferences.getUserProfile()
    fun isUserCaregiver()= user.typeOfUser == CAREGIVER
    fun hasPartner() = user.hasPartner
     val chattingList = MutableLiveData<List<Chatting>>()
     val messagesList = MutableLiveData<List<Message>>()

    fun chatIdPrefix(): String {
        return   if (user.typeOfUser == CAREGIVER){
            user.partnerId!!.take(4)
            "IrSR"
        }else{
             user.id!!.take(4)
            "aEoc"
        }
    }


     fun retrieveChattingList(){
         val chatIdPrefix = chatIdPrefix()
         log("chatIdPrefix => $chatIdPrefix")
        firestore.collection(CHATS)
            .orderBy("__name__")
            .startAt(chatIdPrefix)
            .endAt(chatIdPrefix + '\uf8ff')
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    val list = mutableListOf<Chatting>()

                    for (document in querySnapshot.documents) {
                        val chatting = document.toObject(Chatting::class.java)
                        list.add(chatting!!)
                    }
                    chattingList.value = list
                } else {
                    chattingList.value = mutableListOf()
                }
            }

    }

     fun listenToMessages(){
       val chatId = getChatId()
        firestore.collection(CHATS).document(chatId)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val chatting =
                        documentSnapshot.toObject(Chatting::class.java)?.messages ?: mutableListOf()
                    messagesList.value = chatting
//                    clearEditText.value = true
                } else {

                }
            }
    }


//    fun sendMessage(newMessage:Message,metaDataMessages: MetaDataMessages,chatId: String) {
//
//        val currentMessages = _messages.value?.toMutableList() ?: mutableListOf()
//
//        currentMessages.add(newMessage)
//
//        db.collection(CHATS).document(chatId).set(
//            Chatting(
//                metaDataMessages,
//                currentMessages
//            ))
//            .addOnSuccessListener {
//                log("Message sent successfully")
//            }
//            .addOnFailureListener {
//                log("Failed to send message: ${it.message}")
//            }
//
//    }
    private fun getChatId(): String {

        // pat -> aEoc    care -> IrSR
//        val userId = sharedPreferences.getString(USER_ID).take(4)  //  cur aEoc
//
//        var partnerId = ""
//
//        return if (sharedPreferences.getString(TYPE_OF_USER) == CAREGIVER){
//
//            partnerId = sharedPreferences.getString(ID_PARTNER).take(4) //  aEoc
//            // aEocIrSR
//            partnerId + userId
//        } else {
//            //aEoc
//            userId + partnerId
//        }
        return "aEocIrSR"
    }

}