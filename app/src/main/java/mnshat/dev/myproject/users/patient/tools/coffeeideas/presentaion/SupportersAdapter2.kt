package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.data.entity.RegistrationData
import mnshat.dev.myproject.util.loadImage

class SupportersAdapter2(
    private val supporters: List<RegistrationData>,
    private val itemListener: ItemListener
) : RecyclerView.Adapter<SupportersAdapter2.VH>() {


    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ShapeableImageView>(R.id.imageView)
        val textName = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view_choose_supporter2, parent, false
        )
        return VH(itemView)

    }

    override fun getItemCount() = supporters.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val supporter = supporters[position]
        holder.textName.text = supporter.name
        holder.image.setImageDrawable(null)
        loadImage(holder.itemView.context, supporter.imageUser, holder.image)
        holder.itemView.setOnClickListener {
            itemListener.onItemClick(supporter)
        }

    }
}

interface ItemListener{
    fun onItemClick(supporter: RegistrationData)
}