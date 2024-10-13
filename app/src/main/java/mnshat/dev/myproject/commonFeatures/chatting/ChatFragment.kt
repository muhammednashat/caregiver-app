package mnshat.dev.myproject.commonFeatures.chatting

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.MessagesAdapter
import mnshat.dev.myproject.databinding.FragmentChatBinding
import mnshat.dev.myproject.model.Message
import mnshat.dev.myproject.model.MetaDataMessages
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.USER_IMAGE
import mnshat.dev.myproject.util.USER_NAME
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

        viewModel.clearEditText.observe(viewLifecycleOwner) {
            if (it) {
                binding.messageEditText.text.clear()
                viewModel.resetClearEditText()
            }
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


        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.sendButton.setOnClickListener {
            viewModel.sendMessage(getNewMessage(),getMetaDataMessages(),getChatId())
        }

    }

    private fun getMetaDataMessages(): MetaDataMessages {
        return if (sharedPreferences.getString(TYPE_OF_USER) == CAREGIVER) {
            MetaDataMessages(
                nameCaregiver =sharedPreferences.getString(USER_NAME),
                idCaregiver = sharedPreferences.getString(USER_ID),
                imageCaregiver = sharedPreferences.getString(USER_IMAGE),

                namePatient = namePartner,
                idPatient =partnerId,
                imagePatient =urlImagePartner,

                )
        } else {
            MetaDataMessages(
                nameCaregiver =namePartner,
                idCaregiver =partnerId,
                imageCaregiver = urlImagePartner,

                namePatient =sharedPreferences.getString(USER_NAME),
                idPatient = sharedPreferences.getString(USER_ID),
                imagePatient =sharedPreferences.getString(USER_IMAGE),
            )
        }

    }

    private fun updateUIWithMessages(messages: List<Message>) {

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