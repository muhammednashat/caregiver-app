package mnshat.dev.myproject.users.patient.calender.presentaion

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.calender.domain.entity.CalenderActivity
import mnshat.dev.myproject.util.log

class CalenderActivitiesAdapter(
    private val activities: List<CalenderActivity>,
    private val onActivityClickListener: OnActivityClickListener,
) : RecyclerView.Adapter<CalenderActivitiesAdapter.ViewHolder>() {

    private var chosenActivities = setOf<CalenderActivity>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: ConstraintLayout = itemView.findViewById(R.id.container)
        val text: TextView = itemView.findViewById(R.id.text)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val icChecked: ImageView = itemView.findViewById(R.id.icChecked)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_activity_calender, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val activity = activities[position]
        holder.text.text = activity.nameTask
        holder.imageView.setImageResource(activity.image)
        holder.container.setBackgroundColor(Color.parseColor(activity.background))
        holder.container.setOnClickListener{
            if (position == activities.size - 1){
                onActivityClickListener.createCustomActivity()
            }else{
                if (chosenActivities.contains(activity)){
                    chosenActivities = chosenActivities.minus(activity)
                    holder.icChecked.visibility = View.GONE
                }else{
                    chosenActivities = chosenActivities.plus(activity)
                    holder.icChecked.visibility = View.VISIBLE
                }
                onActivityClickListener.onAddActivity(chosenActivities)
            }
        }

    }
    override fun getItemCount() = activities.size
    fun getChosenActivities() =  chosenActivities

}

interface  OnActivityClickListener{
    fun onAddActivity(activities: Set<CalenderActivity>)
    fun createCustomActivity()
}


