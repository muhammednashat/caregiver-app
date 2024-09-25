package mnshat.dev.myproject.features.chatting

import com.google.firebase.database.FirebaseDatabase
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.MessageAdapter
import mnshat.dev.myproject.databinding.FragmentMessagesListBinding
import mnshat.dev.myproject.util.ENGLISH_KEY


class MessagesListFragment : BaseChattingFragment<FragmentMessagesListBinding>() {

    override fun getLayout() = R.layout.fragment_messages_list

    private lateinit var adapter: MessageAdapter
    private lateinit var db: FirebaseDatabase


    override fun initializeViews() {
        super.initializeViews()
        changeBackgroundIconBasedLang()
    }

    private fun changeBackgroundIconBasedLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }


}