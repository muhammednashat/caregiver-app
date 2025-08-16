package mnshat.dev.myproject.users.patient.tools.breathing.presntaion

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.tools.breathing.data.Duration
import androidx.core.graphics.toColorInt

class MinutesAdapter(

    private val minutesList: List<Duration>,
    private val listener: MinutesListener
) :
    RecyclerView.Adapter<MinutesAdapter.ViewHolder>() {

        private var selectedPosition = RecyclerView.NO_POSITION
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val minuteText: TextView = itemView.findViewById(R.id.text)
        val root: ConstraintLayout = itemView.findViewById(R.id.root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_breathing_duration, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (selectedPosition == position) {

            holder.minuteText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.root.backgroundTintList = ColorStateList.valueOf(
                "#204167".toColorInt()
            )
        } else {
            holder.minuteText.setTextColor("#3d5b7c".toColorInt())
            holder.root.backgroundTintList = ColorStateList.valueOf(
                "#ddecf4".toColorInt()
            )
        }

        holder.minuteText.text = minutesList[position].durationText
        holder.root.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
            listener.onItemClicked(minutesList[position])
        }
    }

    override fun getItemCount(): Int = minutesList.size


}

interface  MinutesListener {
    fun onItemClicked(duration: Duration)
}