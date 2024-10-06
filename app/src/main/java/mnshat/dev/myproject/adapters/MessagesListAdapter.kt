package mnshat.dev.myproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.interfaces.ItemMessagesListClicked
import mnshat.dev.myproject.model.Messages
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.loadImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MessagesListAdapter(
    private val messages: List<Messages>,
    private val context: Context,
    private val sharedPreferencesManager: SharedPreferencesManager,
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
        val lastMessage = messagesSender.messages?.last()
        val metaData = messagesSender.meta!!
        var idPartner =""
        var imagePartner =""
        var namePartner =""
        if (sharedPreferencesManager.getString(TYPE_OF_USER) == CAREGIVER ){
             idPartner = metaData.idPatient!!
             imagePartner = metaData.imagePatient
             namePartner = metaData.namePatient!!
        }else{
            idPartner = metaData.idCaregiver!!
            imagePartner = metaData.imageCaregiver
            namePartner = metaData.nameCaregiver!!
        }

        holder.name.text = namePartner
        holder.date.text = formatTimestamp(lastMessage?.timeStamp)
        holder.message.text = lastMessage?.text
        loadImage(context,imagePartner,holder.imageUser)

        holder.itemView.setOnClickListener {
         itemMessagesListClicked.onItemClicked(namePartner,idPartner,imagePartner)
     }
    }


    private fun formatTimestamp(timeStamp: Long?): String {
        if (timeStamp == null) return "Invalid time"

        val currentTime = System.currentTimeMillis()
        val oneDayInMillis = 24 * 60 * 60 * 1000
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

        return when {
            currentTime - timeStamp == System.currentTimeMillis() -> "الان"
            currentTime - timeStamp < System.currentTimeMillis() -> "اليوم"
            currentTime - timeStamp < 2 * oneDayInMillis -> "الامس"
            else -> dateFormat.format(Date(timeStamp))
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
