package mnshat.dev.myproject.users.patient.calender.presentaion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import mnshat.dev.myproject.R

class DaysAdapter(
    private val days: HashSet<CalendarDay>,
    private val onDayClickListener: OnDayClickListener,
) : RecyclerView.Adapter<DaysAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.text)
        val tracking: TextView = itemView.findViewById(R.id.tracking)
        val image: ImageView = itemView.findViewById(R.id.image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_track_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days.elementAt(position)

//        holder.text.text = day.date.dayOfMonth.toString()
        holder.tracking.setOnClickListener {
            onDayClickListener.onDayClick(day)
        }
    }

    override fun getItemCount() = days.size


}

interface OnDayClickListener {
    fun onDayClick(day: CalendarDay)
}


