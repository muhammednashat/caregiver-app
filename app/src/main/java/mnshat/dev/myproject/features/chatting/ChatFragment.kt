package mnshat.dev.myproject.features.chatting

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.MessageAdapter
import mnshat.dev.myproject.databinding.FragmentChatBinding
import mnshat.dev.myproject.model.Message
import mnshat.dev.myproject.model.Messages
import mnshat.dev.myproject.util.ENGLISH_KEY

class ChatFragment : BaseChattingFragment<FragmentChatBinding>() {

    override fun getLayout() = R.layout.fragment_chat

    private lateinit var adapter: MessageAdapter
    private var db = Firebase.firestore


    override fun initializeViews() {
        super.initializeViews()
        changeBackgroundIconBasedLang()

    }

    private fun changeBackgroundIconBasedLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    fun sendMessage(message: Message, chatId: String) {
        db.collection("chats").document(chatId).get()
            .addOnSuccessListener { documentSnapshot ->
                val messages:MutableList<Message> = if (documentSnapshot.exists()) {
                    documentSnapshot.toObject(Messages::class.java)?.messages ?: mutableListOf()
                }else emptyList<Message>().toMutableList()

                messages.add(message)

                db.collection("chats").document(chatId).set(Messages(messages)).addOnSuccessListener {  }.addOnFailureListener{}

            }.addOnFailureListener {
                println("documentSnapshot not found")

            }
    }


    fun getNewMessage() = Message(
        text = "binding.messageEditText.text.toString()",
        senderId = "12"
    )

    override fun setupClickListener() {
        super.setupClickListener()
        binding.sendButton.setOnClickListener {
            val message = getNewMessage()
            val chatId = getChatId(message.senderId)
            sendMessage(message, chatId)

        }

    }

    private fun getChatId(senderId: String?): String {
        return senderId + "12"
    }
}