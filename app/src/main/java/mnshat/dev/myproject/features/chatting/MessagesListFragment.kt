package mnshat.dev.myproject.features.chatting

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.MessagesListAdapter
import mnshat.dev.myproject.databinding.FragmentMessagesListBinding
import mnshat.dev.myproject.interfaces.ItemMessagesListClicked
import mnshat.dev.myproject.model.Message
import mnshat.dev.myproject.model.Messages
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.log


class MessagesListFragment : BaseChattingFragment<FragmentMessagesListBinding>(),ItemMessagesListClicked {

    override fun getLayout() = R.layout.fragment_messages_list

    override fun initializeViews() {
        super.initializeViews()
        changeBackgroundIconBasedLang()
        viewModel.getMessagesList("123m")
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


    }

    override fun observeViewModel() {
        super.observeViewModel()
        observeGettingMessagesList()

    }

    private fun observeGettingMessagesList() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.messagesListFlow.collect { messagesList ->
                updateUIWithMessagesList(messagesList)
            }
        }
    }

    private fun updateUIWithMessagesList(messagesList: MutableList<Messages>) {
        binding.messagesRecyclerView.apply {
            log("updateUIWithMessagesList")
            adapter = MessagesListAdapter(messagesList,this@MessagesListFragment)
        }
    }

    override fun onItemClicked(name: String, idPartner: String, urlImage: String) {
        val action = MessagesListFragmentDirections.actionMessagesListFragmentToChatFragment(idPartner,urlImage,name)
        findNavController().navigate(action)
    }


}