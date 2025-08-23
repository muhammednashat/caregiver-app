package mnshat.dev.myproject.chatting.presintation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.chatting.entity.Message
import mnshat.dev.myproject.util.getDateAsString


class MessagesAdapter(private val messages: List<Message>,private val userId: String) :
    RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_message, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val message = messages[position]
         if (message.senderId == userId){
            setUpSenderMessage(holder,message)
         }else{
            setUpPartnerMessage(holder,message)
        }
    }

    private fun setUpPartnerMessage(holder: ViewHolder, message: Message) {
        holder.sender.visibility = View.GONE
        holder.partner.visibility = View.VISIBLE
        holder.messagePartner.text = message.text
        holder.datePartner.text = getDateAsString(message.timeStamp ?: 0L)

    }

    private fun setUpSenderMessage(holder: ViewHolder, message: Message) {
        holder.sender.visibility = View.VISIBLE
        holder.partner.visibility = View.GONE
        holder.messageSender.text = message.text
        holder.dateSender.text = getDateAsString(message.timeStamp ?: 0L)
    }


    override fun getItemCount(): Int {
        return messages.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val partner:ConstraintLayout = itemView.findViewById(R.id.partner)
        val sender:ConstraintLayout = itemView.findViewById(R.id.sender)

        var messageSender: TextView = itemView.findViewById<TextView>(R.id.messageSender)
        var messagePartner: TextView = itemView.findViewById<TextView>(R.id.messagePartner)

        var dateSender: TextView = itemView.findViewById<TextView>(R.id.dateSender)
        var datePartner: TextView = itemView.findViewById<TextView>(R.id.datePartner)
    }
}
