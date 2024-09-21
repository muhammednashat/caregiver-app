package mnshat.dev.myproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.util.COMMON_CONTENT
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.loadImage


class RecyclerAdapter(

) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.test3, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return  4
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }






    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById<TextView>(R.id.title)
        var imageView: ImageView = itemView.findViewById<ImageView>(R.id.imageView)
    }
}
