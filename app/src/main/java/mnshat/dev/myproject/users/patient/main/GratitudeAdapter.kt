package mnshat.dev.myproject.users.patient.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.databinding.ItemGratitudeBinding
import mnshat.dev.myproject.model.Gratitude


class GratitudeAdapter(
    private val gratitudeList: List<Gratitude>)
    :
    RecyclerView.Adapter<GratitudeAdapter.GratitudeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GratitudeViewHolder {
        val binding = ItemGratitudeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GratitudeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GratitudeViewHolder, position: Int) {
        val gratitude = gratitudeList[position]
        holder.bind(gratitude)
    }

    override fun getItemCount(): Int = gratitudeList.size

    inner class GratitudeViewHolder(private val binding: ItemGratitudeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gratitude: Gratitude) {
            binding.textViewQuestion.text = gratitude.question
            binding.textViewAnswer.text = gratitude.answer
        }
    }
}
