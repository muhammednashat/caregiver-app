package mnshat.dev.myproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.databinding.ItemGratitudeBinding
import mnshat.dev.myproject.users.patient.tools.gratitude.entity.Gratitude


class GratitudeAdapter(
    private val gratitudeList: List<Gratitude>, private val suggestedQuestion: List<String>
)
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
            binding.textViewQuestion.text = suggestedQuestion[gratitude.index]
            binding.textViewAnswer.text = gratitude.answer
        }
    }
}
