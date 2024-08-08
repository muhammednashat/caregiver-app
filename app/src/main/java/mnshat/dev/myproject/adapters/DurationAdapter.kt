package mnshat.dev.myproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R


class DurationAdapter(private val mData: List<String>) :
    RecyclerView.Adapter<DurationAdapter.ViewHolder>() {
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_duration, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = mData[position]

        if (selectedPosition == position) {
            holder.itemView.layoutParams.height = 200 // Example height
            holder.itemView.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.burnt_orange))
        } else {
            holder.itemView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            holder.itemView.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.white))
        }

        holder.itemView.setOnClickListener { v: View? ->
            notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById<TextView>(R.id.item_text)
    }
}
