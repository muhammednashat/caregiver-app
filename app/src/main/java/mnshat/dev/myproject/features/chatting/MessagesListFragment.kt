package mnshat.dev.myproject.features.chatting

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.MessagesListAdapter
import mnshat.dev.myproject.auth.AgeFragment
import mnshat.dev.myproject.databinding.FragmentMessagesListBinding
import mnshat.dev.myproject.interfaces.ItemMessagesListClicked
import mnshat.dev.myproject.model.Messages
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.HAS_PARTNER
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
        binding.btStartMessaging.setOnClickListener {
            ChooseUserToChatFragment().show(childFragmentManager, ChooseUserToChatFragment::class.java.name)
        }
        binding.icAdd.setOnClickListener {
            ChooseUserToChatFragment().show(childFragmentManager, ChooseUserToChatFragment::class.java.name)
        }

    }
    private fun isHasSupporter() {
        if (sharedPreferences.getBoolean(HAS_PARTNER)) {
            binding.messages.visibility = View.GONE
            binding.noMessage.visibility = View.VISIBLE
        } else {
            binding.messages.visibility = View.GONE
            binding.noMessage.visibility = View.VISIBLE
        }
    }


    override fun observeViewModel() {
        super.observeViewModel()
        observeGettingMessagesList()

    }

    private fun observeGettingMessagesList() {

        viewModel.messagesList.observe(viewLifecycleOwner) {

            updateUIWithMessagesList(it)

        }
        viewModel.messages.observe(viewLifecycleOwner) {

        }

    }

    private fun updateUIWithMessagesList(messagesList: List<Messages>) {
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