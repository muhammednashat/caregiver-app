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
        db = Firebase.database
        val messagesRef = db.reference.child("MESSAGES")
        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesRef, Message::class.java)
            .build()
        adapter = MessageAdapter(options, sharedPreferences.getString(USER_NAME))
        binding.messageRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.messageRecyclerView.adapter = adapter
    }


    override fun setupClickListener() {
        super.setupClickListener()
        binding.sendButton.setOnClickListener {
            val message = Message(
                binding.messageEditText.text.toString(),
                "getUserName()",
            )
            db.reference.child("MESSAGES").push().setValue(message)
            binding.messageEditText.setText("")
        }

    }
}