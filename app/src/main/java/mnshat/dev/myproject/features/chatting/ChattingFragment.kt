package mnshat.dev.myproject.features.chatting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.FriendlyMessageAdapter
import mnshat.dev.myproject.databinding.FragmentChattingBinding
import mnshat.dev.myproject.model.Message
import mnshat.dev.myproject.util.USER_NAME

class ChattingFragment : BaseChattingFragment<FragmentChattingBinding>() {

    override fun getLayout() = R.layout.fragment_chatting
    private lateinit var adapter: FriendlyMessageAdapter
    private lateinit var db: FirebaseDatabase


    override fun initializeViews() {
        super.initializeViews()
        db = Firebase.database
        val messagesRef = db.reference.child("MESSAGES")
        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesRef, Message::class.java)
            .build()
        adapter = FriendlyMessageAdapter(options, sharedPreferences.getString(USER_NAME))
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