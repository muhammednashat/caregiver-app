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
import java.util.Calendar
import java.util.Locale

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
        val calendar = Calendar.getInstance()
        // Get today's date
        val today = CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))

        // Get tomorrow's date
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val tomorrow = CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))

        // Determine the display text
        val displayText = when (day) {
            today -> "Today"
            tomorrow -> "Tomorrow"
            else -> String.format(Locale.getDefault(), "%d/%d/%d", day.month, day.day, day.year)
        }
         holder.text.text = displayText
        holder.tracking.setOnClickListener {
            onDayClickListener.onDayClick(day)
        }
    }

    override fun getItemCount() = days.size


}

interface OnDayClickListener {
    fun onDayClick(day: CalendarDay)
}


