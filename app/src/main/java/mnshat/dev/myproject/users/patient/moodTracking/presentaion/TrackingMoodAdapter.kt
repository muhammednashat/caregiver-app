package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.DayMoodTracking
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EffectingMood
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood

class TrackingMoodAdapter
    (
    private val list: List<DayMoodTracking>,
    private val effectingMoodList: List<EffectingMood>,
    private  val emojisStatus: List<EmojiMood>
) : RecyclerView.Adapter<TrackingMoodAdapter.ViewHolder>() {


    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val day: TextView = itemView.findViewById(R.id.day)
        val moodBefore: TextView = itemView.findViewById(R.id.moodBefore)
        val moodAfter: TextView = itemView.findViewById(R.id.moodAfter)
        val imageBefore: ImageView = itemView.findViewById(R.id.imageBefore)
        val imageAfter: ImageView = itemView.findViewById(R.id.imageAfter)
        val containerBefore:ConstraintLayout = itemView.findViewById(R.id.containerBefore)
        val containerAfter:ConstraintLayout = itemView.findViewById(R.id.containerAfter)
        val recyclerViewEffectingMood:RecyclerView = itemView.findViewById(R.id.recyclerViewEffectingMood)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_tracking_mood, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val currentDay = list[position]
    setUpUi(holder,currentDay)

    }

    private fun setUpUi(holder: ViewHolder, trackingMood: DayMoodTracking) {

        val preMood =emojisStatus [trackingMood.preMoodIndex!!]
        val postMood = emojisStatus[trackingMood.postMoodIndex!!]
        holder.day.text = trackingMood.day.toString()
        holder.moodBefore.text = preMood.name
        holder.imageBefore.setImageResource(preMood.emoji)
        holder.containerBefore.setBackgroundColor(Color.parseColor(preMood.backgroundColor))
        holder.containerAfter.setBackgroundColor(Color.parseColor(postMood.backgroundColor))
        holder.moodAfter.text = postMood.name
        holder.imageAfter.setImageResource(postMood.emoji)
    }

    private fun setUpRecyclerViewEffectingMood(holder: ViewHolder, trackingMood: DayMoodTracking) {
//        val  fd = trackingMood.reasons
//
//        val list = effectingMoodList.filter {   }
//        val effectingAdapter = EffectingMoodAdapter(list)
//        val layoutManager = GridLayoutManager(holder.recyclerViewEffectingMood.context, 3)
//        holder.recyclerViewEffectingMood.layoutManager = layoutManager
//        holder.recyclerViewEffectingMood.adapter = effectingAdapter
    }
}