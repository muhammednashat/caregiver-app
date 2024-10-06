package mnshat.dev.myproject.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.interfaces.ItemMessagesListClicked
import mnshat.dev.myproject.model.Message
import mnshat.dev.myproject.model.Messages
import mnshat.dev.myproject.util.getDateAsString


class MessagesListAdapter(
    private val messages: List<Messages>,
    private val itemMessagesListClicked: ItemMessagesListClicked
) :
    RecyclerView.Adapter<MessagesListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_messages, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val messagesSender = messages[position]
        val lastMessage = messagesSender.messages.last()
       holder.name.text = messagesSender.namePartner
       holder.date.text = lastMessage.timeStamp.toString()
       holder.message.text = lastMessage.text

     holder.itemView.setOnClickListener {
         itemMessagesListClicked.onItemClicked(messagesSender.namePartner!!,messagesSender.idPartner!!,messagesSender.urlImagePartner)
     }
    }




    override fun getItemCount(): Int {
        return messages.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView = itemView.findViewById<TextView>(R.id.name)
        var date: TextView = itemView.findViewById<TextView>(R.id.date)
        var message: TextView = itemView.findViewById<TextView>(R.id.message)
        var imageUser: ShapeableImageView = itemView.findViewById<ShapeableImageView>(R.id.image_user)



    }
}
