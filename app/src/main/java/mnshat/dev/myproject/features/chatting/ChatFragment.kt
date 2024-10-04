package mnshat.dev.myproject.features.chatting

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.MessagesAdapter
import mnshat.dev.myproject.databinding.FragmentChatBinding
import mnshat.dev.myproject.model.Message
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.log

class ChatFragment : BaseChattingFragment<FragmentChatBinding>() {

    override fun getLayout() = R.layout.fragment_chat
    private var partnerId: String = ""
    private var urlImagePartner: String = ""
    private var namePartner: String = ""
    override fun initializeViews() {
        super.initializeViews()
        retrieveDataFromArguments()
        setUpUi()
        changeBackgroundIconBasedLang()
        getMessages(getChatId())
    }

    private fun setUpUi() {
        binding.name.text = namePartner
    }

    private fun retrieveDataFromArguments() {
        val args: ChatFragmentArgs by navArgs()
        partnerId = args.partnerId
        urlImagePartner = args.urlImagePartner
        namePartner = args.namePartner
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
            log("observeGettingMessages")

            viewModel.messagesFlow.collect { messages ->
                log("observeGettingMessages")
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
            viewModel.sendMessage(getNewMessage(),namePartner,urlImagePartner,partnerId,getChatId())
        }

    }

    private fun updateUIWithMessages(messages: MutableList<Message>) {
        log("updateUIWithMessages")

        binding.messageRecyclerView.apply {
            scrollToPosition((messages.size).minus(1))
            adapter = MessagesAdapter(messages,sharedPreferences.getString(USER_ID))

        }

    }

    private fun getChatId(): String {
        val userId = sharedPreferences.getString(USER_ID).take(4)
        val partnerId = partnerId.take(4)

        return if (sharedPreferences.getString(TYPE_OF_USER) == CAREGIVER){

            log(partnerId + userId)
            partnerId + userId
        } else {
            log(userId + partnerId)
            userId + partnerId
        }
    }
}