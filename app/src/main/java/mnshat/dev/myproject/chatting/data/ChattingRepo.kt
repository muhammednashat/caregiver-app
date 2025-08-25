package mnshat.dev.myproject.chatting.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
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

   private fun chatIdPrefix(): String {
        return  if (user.typeOfUser == CAREGIVER){
            user.partnerId!!.take(4)
        }else{
             user.id!!.take(4)
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

     fun listenToMessages(partnerId:String){
       val chatId = getChatId(partnerId)
         log("chatId => $chatId")
        firestore.collection(CHATS).document(chatId)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    log("documentSnapshot => true")
                    val chatting =
                        documentSnapshot.toObject(Chatting::class.java)?.messages ?: mutableListOf()
                    messagesList.value = chatting

                } else {
                    log("documentSnapshot => false")
                    messagesList.value = emptyList()

                }
            }
    }



    private fun getChatId(partnerId:String): String {

        val userId = user.id!!.take(4)
        var partnerId = partnerId.take(4)

        return if (isUserCaregiver()){

            partnerId = user.partnerId!!.take(4) //  aEoc
            partnerId + userId
        } else {
            userId + partnerId
        }

    }

   suspend fun sendMessage(message: Message, messages: MetaDataMessages , partnerId:String ) {
        val chatId = getChatId(partnerId)
        val messagesList = messagesList.value?.toMutableList() ?: mutableListOf()
         messagesList.add(message)
        val chatting = Chatting(messages, messagesList)
        firestore.collection(CHATS).document(chatId).set(chatting).await()

    }

}