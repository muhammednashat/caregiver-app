package mnshat.dev.myproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.interfaces.ItemStepsClicked
import mnshat.dev.myproject.model.Step

class StepsAdapter(
    private val steps: List<Step>,
    private val itemStepsClicked: ItemStepsClicked

) :
    RecyclerView.Adapter<StepsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val step: TextView = itemView.findViewById(R.id.step)
        val title: TextView = itemView.findViewById(R.id.title)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val arrow: ImageView = itemView.findViewById(R.id.arrow)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_caregiver_tools_steps, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step = steps[position]
        holder.step.text = step.step
        holder.title.text = step.title
        holder.imageView.setImageResource(step.image)
        holder.arrow.setOnClickListener {
            itemStepsClicked.onItemClicked(position)
        }

    }



    override fun getItemCount() = steps.size
}
