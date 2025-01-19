package mnshat.dev.myproject.users.patient.points.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.util.log

class AdapterPoints(private val numberTracking: Int) : RecyclerView.Adapter<AdapterPoints.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: ConstraintLayout = itemView.findViewById(R.id.root)
        val day: TextView = itemView.findViewById(R.id.day)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_point, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.day.text = holder.itemView.context.getString(R.string.day,position+1)
        log(position.toString())
        log(numberTracking.toString())
        if (position < numberTracking){
            log("if position")
            holder.imageView.setImageResource(R.drawable.star2)
            holder.root.setBackgroundResource(R.drawable.image_background2w)
        }else{
            holder.imageView.setImageResource(R.drawable.icon_look2)
            holder.root.setBackgroundResource(R.drawable.image_background4j)
        }
    }

    override fun getItemCount() = 30

}