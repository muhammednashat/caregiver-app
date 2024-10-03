package mnshat.dev.myproject.features.chatting

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.MessagesAdapter
import mnshat.dev.myproject.databinding.FragmentChatBinding
import mnshat.dev.myproject.model.Message
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.USER_ID

class ChatFragment : BaseChattingFragment<FragmentChatBinding>() {

    override fun getLayout() = R.layout.fragment_chat

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
        senderId = sharedPreferences.getString(USER_ID)
    )

    override fun setupClickListener() {
        super.setupClickListener()
        binding.sendButton.setOnClickListener {
            viewModel.sendMessage( getNewMessage(),getChatId())
        }

    }

    private fun updateUIWithMessages(messages: MutableList<Message>) {

        binding.messageRecyclerView.apply {
            adapter = MessagesAdapter(messages,sharedPreferences.getString(USER_ID))
        }

    }

    private fun getChatId(): String {
        return if (sharedPreferences.getString(TYPE_OF_USER) == CAREGIVER){
            sharedPreferences.getString(USER_ID) + "12"
        }else sharedPreferences.getString(USER_ID) + "12"
    }
}