package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R

class SuggestedSympathyAdapter (private val phrases: List<String>): RecyclerView.Adapter<SuggestedSympathyAdapter.ViewHolder> (){

    inner  class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val root: ConstraintLayout = itemView.findViewById(R.id.root)
        val text: TextView = itemView.findViewById(R.id.text)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_suggested_phrases_sympathy, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount () = phrases.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val text = phrases[position]
        holder.text.text = text

    }

}