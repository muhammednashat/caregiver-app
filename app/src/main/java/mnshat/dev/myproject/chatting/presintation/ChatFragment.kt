package mnshat.dev.myproject.chatting.presintation

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentChatBinding
import mnshat.dev.myproject.chatting.entity.Message
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log

class ChatFragment : BaseFragment() {

    private val viewModel: ChatViewModel by activityViewModels()
    private lateinit var binding: FragmentChatBinding
    private var partnerId: String = ""
    private var urlImagePartner: String = ""
    private var namePartner: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        checkInternetConnection()

        return binding.root
    }

    private fun checkInternetConnection() {
        if (isConnected()) {
            initializeViews()
        } else {
            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.no_internet_connection))
            .setMessage(getString(R.string.please_check_your_internet_connection_and_try_again))
            .setPositiveButton(getString(R.string.try_again)) { dialog, _ ->
                checkInternetConnection()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }


    private fun initializeViews() {
        showProgressDialog()
        retrieveDataFromArguments()
        initUserData()
        viewModel.listenToMessages()
        observeViewModel()
        addTextChangedListener()
    }

    private fun retrieveDataFromArguments() {
        val args: ChatFragmentArgs by navArgs()
        partnerId = args.partnerId
        urlImagePartner = args.urlImagePartner
        namePartner = args.namePartner
    }

    private fun initUserData() {
        binding.name.text = namePartner
        loadImage(requireActivity(), urlImagePartner, binding.imageUser)
    }

    private fun addTextChangedListener() {

//        binding.messageEditText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val inputText = s.toString()
//                if (inputText.isNotEmpty()){
//                    binding.sendButton.visibility = View.VISIBLE
//                }else{
//                    binding.sendButton.visibility = View.GONE
//                }
//
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        })
    }


    private fun observeViewModel() {

        viewModel.messages.observe(viewLifecycleOwner) {
            it?.let {
                log("messages => $it")
                updateUIWithMessages(it)
                dismissProgressDialog()
            }
        }

//        viewModel.clearEditText.observe(viewLifecycleOwner) {
//            if (it) {
//                binding.messageEditText.text.clear()
//                viewModel.resetClearEditText()
//            }
//        }
    }

    private fun updateUIWithMessages(messages: List<Message>) {

        binding.messageRecyclerView.apply {
            scrollToPosition((messages.size).minus(1))
            adapter = MessagesAdapter(messages, viewModel.user.id!!)

        }

    }


    private fun getNewMessage() = Message(
//        text = binding.messageEditText.text.toString(),
//        senderId = sharedPreferences.getString(USER_ID)
    )

    private fun setupClickListener() {

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.sendButton.setOnClickListener {
//            viewModel.sendMessage(getNewMessage(),getMetaDataMessages(),getChatId())
        }

    }

//    private fun getMetaDataMessages(): MetaDataMessages {
//        return if (sharedPreferences.getString(TYPE_OF_USER) == CAREGIVER) {
//            MetaDataMessages(
//                nameCaregiver =sharedPreferences.getString(USER_NAME),
//                idCaregiver = sharedPreferences.getString(USER_ID),
//                imageCaregiver = sharedPreferences.getString(USER_IMAGE),
//
//                namePatient = namePartner,
//                idPatient =partnerId,
//                imagePatient =urlImagePartner,
//
//                )
//        } else {
//            MetaDataMessages(
//                nameCaregiver =namePartner,
//                idCaregiver =partnerId,
//                imageCaregiver = urlImagePartner,
//
//                namePatient =sharedPreferences.getString(USER_NAME),
//                idPatient = sharedPreferences.getString(USER_ID),
//                imagePatient =sharedPreferences.getString(USER_IMAGE),
//            )
//        }

//    }


}