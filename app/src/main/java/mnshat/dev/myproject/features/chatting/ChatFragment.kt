package mnshat.dev.myproject.features.chatting

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.MessagesAdapter
import mnshat.dev.myproject.databinding.FragmentChatBinding
import mnshat.dev.myproject.model.Message
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.IMAGE_PARTNER
import mnshat.dev.myproject.util.NAME_PARTNER
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log

class ChatFragment : BaseChattingFragment<FragmentChatBinding>() {

    override fun getLayout() = R.layout.fragment_chat
    private var partnerId: String = ""
    private var urlImagePartner: String = ""
    private var namePartner: String = ""
    override fun initializeViews() {
        super.initializeViews()
        retrieveDataFromArguments()
        initUserData()
        changeBackgroundIconBasedLang()
        getMessages(getChatId())
        addTextChangedListener ()
    }

    private fun addTextChangedListener (){
        binding.messageEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputText = s.toString()
                if (inputText.isNotEmpty()){
                    binding.sendButton.visibility = View.VISIBLE
                }else{
                    binding.sendButton.visibility = View.GONE
                }


            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
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

        viewModel.messages.observe(viewLifecycleOwner){
           it?.let {   updateUIWithMessages(it)}
        }
    }

    private fun initUserData() {
        binding.name.text = namePartner
        loadImage(requireActivity(),urlImagePartner,binding.imageUser)
    }


    private fun getNewMessage() = Message(
        text = binding.messageEditText.text.toString(),
        senderId = sharedPreferences.getString(USER_ID)
    )

    override fun setupClickListener() {
        super.setupClickListener()

        binding.sendButton.setOnClickListener {
            log("${binding.messageEditText.text.toString()}   ${sharedPreferences.getString(USER_ID)} ")

            viewModel.sendMessage(getNewMessage(),namePartner,urlImagePartner,partnerId,getChatId())
        }

    }

    private fun updateUIWithMessages(messages: List<Message>) {
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