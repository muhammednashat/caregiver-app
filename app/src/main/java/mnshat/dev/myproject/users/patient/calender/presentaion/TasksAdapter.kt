package mnshat.dev.myproject.users.patient.calender.presentaion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.calender.domain.entity.TaskEntity

class TasksAdapter (
    private val tasks:List<TaskEntity>
):RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

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
        holder.title.text = task.nameTask
        holder.image.setImageResource(task.image)
        holder.radioButton.isChecked = task.isCompleted

     setUpRadioButton(holder,holder.radioButton,task)

    }


    override fun getItemCount() = tasks.size

    private fun setUpRadioButton(holder: ViewHolder, radioButton: RadioButton, task: TaskEntity) {

    }

}