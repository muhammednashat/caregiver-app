package mnshat.dev.myproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.model.Post


class SharingAdapter(private val sharingList: List<Post>) :
    RecyclerView.Adapter<SharingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_sharing, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val content = sharingList[position]

    }




    override fun getItemCount(): Int {
        return sharingList.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var type: TextView = itemView.findViewById<TextView>(R.id.type)
        var text: TextView = itemView.findViewById<TextView>(R.id.text)
        var display: TextView = itemView.findViewById<TextView>(R.id.display)
        var imageView: ImageView = itemView.findViewById<ImageView>(R.id.imageView)


    }
}
