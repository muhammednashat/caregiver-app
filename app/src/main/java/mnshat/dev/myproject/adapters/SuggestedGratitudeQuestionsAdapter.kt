package mnshat.dev.myproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.interfaces.ItemSuggestedGratitudeQuestionsClicked
import mnshat.dev.myproject.interfaces.ItemSupplicationClicked
import mnshat.dev.myproject.model.Supplication

class SuggestedGratitudeQuestionsAdapter(
    private val items: List<String>,
    private val itemSuggestedGratitudeQuestionsClicked: ItemSuggestedGratitudeQuestionsClicked

) :
    RecyclerView.Adapter<SuggestedGratitudeQuestionsAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.textName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_suggested_questions, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textName.text = item
        holder.itemView.setOnClickListener {
            itemSuggestedGratitudeQuestionsClicked.onItemClicked(item)
        }
    }

    override fun getItemCount() = items.size
}
