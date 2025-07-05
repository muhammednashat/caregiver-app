package mnshat.dev.myproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.data.entity.RegistrationData
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log

class ChooseSupporterAdapter(
    val context: Context,
    private val supporters: List<RegistrationData>

) :
    RecyclerView.Adapter<ChooseSupporterAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val iconCheck: ImageView = itemView.findViewById(R.id.iconCheck)
    }

    private  var supportersList:MutableList<String> = emptyList<String>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_choose_supporter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val supporter = supporters[position]
        holder.name.text = supporter.name
        loadImage(context, supporter.imageUser, holder.imageView)

        val selectedBackground = context.getColor(R.color.green)
        val defaultBackground = context.getColor(R.color.black)

        var isSelected = false
        holder.itemView.setOnClickListener {
            isSelected = !isSelected

         //ToDo fix , case when adding at index 1 before index 0

            if (isSelected) {
                log("selected $position")
                supportersList.add(position,supporter.email!!)
//                holder.iconCheck.visibility = View.VISIBLE
                holder.name.setTextColor(selectedBackground)
            } else {
                log("no selected $position")
                supportersList.removeAt(position)
//                holder.iconCheck.visibility = View.GONE
                holder.name.setTextColor(defaultBackground)
            }
        }
    }

    fun getSelectedSupporters()= supportersList

    override fun getItemCount(): Int {
        log("getItemCount ${supporters.size}")
        return supporters.size
    }
}
