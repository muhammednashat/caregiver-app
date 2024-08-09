package mnshat.dev.myproject.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.model.Duration


class DurationAdapter(private val durations: List<Duration>) :
    RecyclerView.Adapter<DurationAdapter.ViewHolder>() {
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_duration, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val duration = durations[position]
        holder.textView.text = duration.text

        if (selectedPosition == position) {
            holder.imageView.setImageResource(R.drawable.icon_accept8)
            holder.textView.setTextColor(Color.parseColor("#438fb3"))
            holder.itemView.setBackgroundDrawable(holder.itemView.context.resources.getDrawable(R.drawable.corner_four_dark_blue))


        } else {
            holder.imageView.setImageResource(R.drawable.circle_white)
            holder.textView.setTextColor(Color.BLACK)
            holder.itemView.setBackgroundDrawable(holder.itemView.context.resources.getDrawable(R.drawable.corner_four_gray))

        }

        holder.itemView.setOnClickListener { v: View? ->
            notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int {
        return durations.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById<TextView>(R.id.item_text)
        var imageView: ImageView = itemView.findViewById<ImageView>(R.id.item_image)
    }
}
