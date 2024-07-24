package mnshat.dev.myproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.interfaces.ItemSupplicationClicked
import mnshat.dev.myproject.model.Supplication

class SuggestedSupplicationAdapter(
    private val items: List<Supplication>,
    private val itemSupplicationClicked: ItemSupplicationClicked

) :
    RecyclerView.Adapter<SuggestedSupplicationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.textName)
        val icon: ImageView = itemView.findViewById(R.id.icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_suggested_supplications, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textName.text = item.name
        holder.icon.setOnClickListener {
            itemSupplicationClicked.onItemClicked(item)
        }
    }

    override fun getItemCount() = items.size
}
