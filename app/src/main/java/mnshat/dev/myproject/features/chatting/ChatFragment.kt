package mnshat.dev.myproject.features.chatting

import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.MessageAdapter
import mnshat.dev.myproject.databinding.FragmentChatBinding
import mnshat.dev.myproject.model.Message
import mnshat.dev.myproject.util.USER_NAME

class ChatFragment : BaseChattingFragment<FragmentChatBinding>() {

    override fun getLayout() = R.layout.fragment_chat
    private lateinit var adapter: MessageAdapter
    private lateinit var db: FirebaseDatabase


    override fun initializeViews() {
        super.initializeViews()

    }


    override fun setupClickListener() {
        super.setupClickListener()


    }
}