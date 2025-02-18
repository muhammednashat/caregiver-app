package mnshat.dev.myproject.users.patient.dailyprogram.presentaion


import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.Task

class SuggestedChallengesAdapter(
    private val tasks: List<Task>,
    private var selectedPosition:Int,
    private val lang :String
) :
    RecyclerView.Adapter<SuggestedChallengesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val text: TextView = itemView.findViewById(R.id.text)
        val image: ImageView = itemView.findViewById(R.id.imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_suggested_challengs, parent, false)
        return ViewHolder(view)

    }

    fun getSelectedPosition()= selectedPosition
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        val text =  if (lang == "en") task.enDescription else task.arDescription
        holder.title.text =  if (lang == "en") task.enTitle else task.arTitle
        holder.text.text =  Html.fromHtml(text)
        Glide.with(holder.itemView.context).load(task.image).into(holder.image)

        if (selectedPosition == position) {
//            holder.textView.setTextColor(Color.parseColor("#438fb3"))
            holder.itemView.setBackgroundDrawable(holder.itemView.context.resources.getDrawable(R.drawable.corner_four_dark_blue))
        } else {
//            holder.textView.setTextColor(Color.BLACK)
            holder.itemView.setBackgroundDrawable(holder.itemView.context.resources.getDrawable(R.drawable.corner_four_gray))
        }


//
//        holder.textView.text = item
//        holder.itemView.setOnClickListener {
//            notifyItemChanged(selectedPosition)
//            selectedPosition = holder.adapterPosition
//            notifyItemChanged(selectedPosition)
//        }
    }

    override fun getItemCount() = tasks.size
}
