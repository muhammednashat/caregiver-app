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
    private val tasks:MutableList<TaskEntity>,
    private val onItemClickListener: OnItemClickListener
):RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.image)
        val imageChecked: ImageView = itemView.findViewById(R.id.image_checked)
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

        if (task.isCompleted) {
            holder.imageChecked.setImageResource(R.drawable.icon_checked22)
        } else {
            holder.imageChecked.setImageResource(R.drawable.circle_white_border_blue)
        }
        holder.itemView.setOnClickListener {
            task.isCompleted = !task.isCompleted
            onItemClickListener.updateTaskStatus(task)
            notifyItemChanged(position)
        }


    }
    override fun getItemCount() = tasks.size

    fun removeItem(position: Int,) {
        onItemClickListener.deleteTask(tasks[position].taskId)
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }

  fun getTasks () = tasks
}


interface OnItemClickListener {
    fun updateTaskStatus(task: TaskEntity)
    fun deleteTask(taskId: Int)

}