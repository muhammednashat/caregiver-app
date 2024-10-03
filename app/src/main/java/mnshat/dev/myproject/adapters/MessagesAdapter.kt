package mnshat.dev.myproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.model.Message


class MessagesAdapter(private val messages: MutableList<Message>,private val userId: String) :
    RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    override fun getItemCount(): Int {
        return messages.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageTextView: TextView = itemView.findViewById<TextView>(R.id.messageTextView)
        var date: TextView = itemView.findViewById<TextView>(R.id.date)
    }
}
