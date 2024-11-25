package mnshat.dev.myproject.users.patient.calender.presentaion

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.model.Step
import mnshat.dev.myproject.users.patient.calender.domain.CalenderActivity

class CalenderActivitiesAdapter(
    private val activities: List<CalenderActivity>,
) :

    RecyclerView.Adapter<CalenderActivitiesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: ConstraintLayout = itemView.findViewById(R.id.container)
        val text: TextView = itemView.findViewById(R.id.text)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_activity_calender, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val activity = activities[position]
       holder.text.text = activity.text
       holder.imageView.setImageResource(activity.image)
//       holder.container.setBackgroundResource(Color.parseColor(activity.background))

    }


    override fun getItemCount() = activities.size
}


