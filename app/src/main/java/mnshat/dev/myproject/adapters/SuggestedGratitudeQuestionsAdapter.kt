package mnshat.dev.myproject.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R

class SuggestedGratitudeQuestionsAdapter(
    private val items: List<String>,
    private var selectedPosition:Int,
) :
    RecyclerView.Adapter<SuggestedGratitudeQuestionsAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_suggested_questions, parent, false)
        return ViewHolder(view)

    }

    fun getSelectedPosition()= selectedPosition
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        if (selectedPosition == position) {
            holder.textView.setTextColor(Color.parseColor("#438fb3"))
            holder.itemView.setBackgroundDrawable(holder.itemView.context.resources.getDrawable(R.drawable.corner_four_dark_blue))
        } else {
            holder.textView.setTextColor(Color.BLACK)
            holder.itemView.setBackgroundDrawable(holder.itemView.context.resources.getDrawable(R.drawable.corner_four_gray))
        }



        holder.textView.text = item
        holder.itemView.setOnClickListener {
            notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount() = items.size
}
