package mnshat.dev.myproject.features.chatting

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.MessageAdapter
import mnshat.dev.myproject.databinding.FragmentChatBinding
import mnshat.dev.myproject.model.Message
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.log

class ChatFragment : BaseChattingFragment<FragmentChatBinding>() {

    override fun getLayout() = R.layout.fragment_chat

    private lateinit var adapter: MessageAdapter



    override fun initializeViews() {
        super.initializeViews()
        changeBackgroundIconBasedLang()
        getMessages(getChatId())
    }



    private fun changeBackgroundIconBasedLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    private fun getMessages(chatId: String) {

        viewModel.getMessages(chatId)

    }

    override fun observeViewModel() {
        super.observeViewModel()
       observeGettingMessages()

    }

    private fun observeGettingMessages() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.messagesFlow.collect { messages ->
                updateUIWithMessages(messages)
            }
        }
    }


    private fun getNewMessage() = Message(
        text = "binding.messageEditText.text.toString()",
        senderId = "123"
    )

    override fun setupClickListener() {
        super.setupClickListener()
        binding.sendButton.setOnClickListener {
            log("sendButton ")
            viewModel.sendMessage( getNewMessage(),getChatId())
        }

    }

    private fun updateUIWithMessages(messages: MutableList<Message>) {
    log(messages.size.toString() + " q23234" )
    }

    private fun getChatId(): String {
        return "1df2" + "12"
    }
}