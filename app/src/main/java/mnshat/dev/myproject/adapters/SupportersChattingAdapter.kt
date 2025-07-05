package mnshat.dev.myproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.interfaces.ItemMessagesListClicked
import mnshat.dev.myproject.auth.data.entity.RegistrationData
import mnshat.dev.myproject.util.loadImage


class SupportersChattingAdapter(
    private val itemList: List<RegistrationData>,
    private val context: Context,
    private val itemMessagesListClicked :ItemMessagesListClicked
) :
    RecyclerView.Adapter<SupportersChattingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_supporter_chatting, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.name.text = item.name
        loadImage(context,item.imageUser,holder.imageView)
       holder.itemView.setOnClickListener{
           itemMessagesListClicked.onItemClicked(item.name!!,item.id!!,item.imageUser!!)
       }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById<TextView>(R.id.name)
        var imageView: ImageView = itemView.findViewById<ImageView>(R.id.image_user)
    }
}

