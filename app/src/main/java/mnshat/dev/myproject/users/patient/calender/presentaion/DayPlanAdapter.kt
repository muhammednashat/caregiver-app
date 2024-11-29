package mnshat.dev.myproject.users.patient.calender.presentaion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.users.patient.calender.domain.entity.CalenderDayTask
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log

class DayPlanAdapter(
    private val tasks: List<CalenderDayTask>
) :
    RecyclerView.Adapter<DayPlanAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.image)
        val radioButton: RadioButton = itemView.findViewById(R.id.radioButton)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_day_task_calender, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val task = tasks[position]
       holder.title.text = task.title
       if (task.status){
           holder.radioButton.isChecked = true
       }
    }


    override fun getItemCount(): Int {
        return tasks.size
    }
}
