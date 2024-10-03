package mnshat.dev.myproject.features.chatting

import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentMessagesListBinding
import mnshat.dev.myproject.util.ENGLISH_KEY


class MessagesListFragment : BaseChattingFragment<FragmentMessagesListBinding>() {

    override fun getLayout() = R.layout.fragment_messages_list

    override fun initializeViews() {
        super.initializeViews()
        changeBackgroundIconBasedLang()
    }

    private fun changeBackgroundIconBasedLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    override fun setupClickListener() {
        super.setupClickListener()
      binding.go.setOnClickListener {
          val action = MessagesListFragmentDirections.actionMessagesListFragmentToChatFragment("12")
          findNavController().navigate(action)
      }
    }


}