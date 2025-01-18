package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EffectingMood

class EffectingMoodAdapter (
    private val list: List<EffectingMood>,
    val isClickable: Boolean = true,
) : RecyclerView.Adapter<EffectingMoodAdapter.ViewHolder>() {

    private var chosenReasons = setOf<Int>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val icon: ImageView = itemView.findViewById(R.id.icon)
        val icChecked: ImageView = itemView.findViewById(R.id.icChecked)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_effecting_mood, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.title.text = data.title
        holder.icon.setImageResource(data.icon)
        if (isClickable){
        holder.itemView.setOnClickListener {
            if (chosenReasons.contains(position)) {
                holder.icChecked.visibility = View.GONE
                chosenReasons = chosenReasons.minus(position)
            } else {
                holder.icChecked.visibility = View.VISIBLE
                chosenReasons = chosenReasons.plus(position)
            }
        }
        }
    }

    fun getChosenReasons() = chosenReasons

    override fun getItemCount() = list.size

}

interface OnItemListener {
    fun onItemClicked(size: Int)
}


