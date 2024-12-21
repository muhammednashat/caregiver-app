package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood

class EmojisAdapter(
    private val emojis: List<EmojiMood>,
    private val onEmojiClickListener: OnEmojiClickListener,
) : RecyclerView.Adapter<EmojisAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.mood)
        val emoji: ImageView = itemView.findViewById(R.id.icon)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_mood, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val emoji = emojis[position]
     holder.title.text = emoji.title
     holder.emoji.setImageResource(emoji.emoji)
      holder.itemView.setOnClickListener{
          onEmojiClickListener.onEmojiClicked(emoji)
      }

    }
    override fun getItemCount() = emojis.size

}

interface  OnEmojiClickListener{
    fun onEmojiClicked(emoji: EmojiMood)
}


