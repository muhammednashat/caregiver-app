package mnshat.dev.myproject.commonFeatures.chatting

import android.view.View
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.MessagesListAdapter
import mnshat.dev.myproject.databinding.FragmentMessagesListBinding
import mnshat.dev.myproject.interfaces.ItemMessagesListClicked
import mnshat.dev.myproject.model.Messages
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.ID_PARTNER
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.log


class MessagesListFragment : BaseChattingFragment<FragmentMessagesListBinding>(),ItemMessagesListClicked {

    override fun getLayout() = R.layout.fragment_messages_list

    override fun initializeViews() {
        super.initializeViews()
        changeBackgroundIconBasedLang()
        viewModel.getMessagesList(getChatId())
    }

    private fun changeBackgroundIconBasedLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    override fun setupClickListener() {
        super.setupClickListener()

        binding.icBack.setOnClickListener{

          }
        binding.btStartMessaging.setOnClickListener {
            ChooseUserToChatFragment().show(childFragmentManager, ChooseUserToChatFragment::class.java.name)
        }
        binding.icAdd.setOnClickListener {
            ChooseUserToChatFragment().show(childFragmentManager, ChooseUserToChatFragment::class.java.name)
        }

    }
    override fun observeViewModel() {
        super.observeViewModel()
        observeGettingMessagesList()
    }

    private fun observeGettingMessagesList() {
        viewModel.messagesList.observe(viewLifecycleOwner) {
             if (it.isEmpty()) {
                 binding.messages.visibility = View.GONE
                 binding.noMessage.visibility = View.VISIBLE
             }else{
                 updateUIWithMessagesList(it)
                 binding.noMessage.visibility = View.GONE
                 binding.messages.visibility = View.VISIBLE
             }
        }

    }

    private fun updateUIWithMessagesList(messagesList: List<Messages>) {
        binding.messagesRecyclerView.apply {
            adapter = MessagesListAdapter(messagesList,requireActivity(),sharedPreferences,this@MessagesListFragment)
        }
    }

    override fun onItemClicked(name: String, idPartner: String, urlImage: String) {

        val action = MessagesListFragmentDirections.actionMessagesListFragmentToChatFragment(idPartner,urlImage,name)
        findNavController().navigate(action)

    }


    private fun getChatId(): String {

        // pat -> aEoc    care -> IrSR
        val userId = sharedPreferences.getString(USER_ID).take(4)  //  cur aEoc

        var partnerId = ""

        return if (sharedPreferences.getString(TYPE_OF_USER) == CAREGIVER){

            partnerId = sharedPreferences.getString(ID_PARTNER).take(4) //  aEoc
            // aEocIrSR
            partnerId + userId
        } else {
            //aEoc
            userId + partnerId
        }
    }


}