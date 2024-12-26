package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood

class SuggestionsAdapter(
  private val emoji:EmojiMood
) : RecyclerView.Adapter<SuggestionsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val index: TextView = itemView.findViewById(R.id.index)
        val todoText: TextView = itemView.findViewById(R.id.todo_text)
        val tip: TextView = itemView.findViewById(R.id.tip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_resault_mood, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.index.text = "${position + 1}"
        holder.index.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(emoji.buttonColor))
        holder.todoText.text = emoji.suggestion[position].text
        if (emoji.suggestion[position].tip.isNotEmpty()) {
            holder.tip.text = emoji.suggestion[position].tip
            holder.tip.setTextColor(Color.parseColor(emoji.buttonColor))
            holder.tip.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(emoji.tipBackgroundColor))
            holder.tip.visibility = View.VISIBLE

        }
    }
    override fun getItemCount() = emoji.suggestion.size

}



