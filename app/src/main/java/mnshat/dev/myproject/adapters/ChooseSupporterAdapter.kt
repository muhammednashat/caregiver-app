package mnshat.dev.myproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.interfaces.ItemSupplicationClicked
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.loadImage

class ChooseSupporterAdapter(
    val context: Context,
    private val supporters: List<RegistrationData>

) :
    RecyclerView.Adapter<ChooseSupporterAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_choose_supporter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val supporter = supporters[position]
        holder.name.text = supporter.name
        loadImage(context, supporter.imageUser, holder.imageView)
    }

    override fun getItemCount() = supporters.size
}
