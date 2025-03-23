package mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.Sound
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.util.COMMON_CONTENT
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.loadImage


class SoundsAdapter(
    private val sounds: List<Sound>,
    private val context: Context,
    private val onItemSoundClicked: OnItemSoundClicked
) :
    RecyclerView.Adapter<SoundsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sound, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // card
        val sound = sounds?.get(position)!!
         holder.title.text = sound?.name;

        holder.imageView.setImageResource(sound.image)
        holder.itemView.setOnClickListener {
            onItemSoundClicked.onItemClicked(sound.sound)
    }

    }

    fun prin(){
        println("")

    }

    override fun getItemCount(): Int {
        return sounds.size!!
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById<TextView>(R.id.title)
        var imageView: ImageView = itemView.findViewById<ImageView>(R.id.imageView)
    }
}


interface OnItemSoundClicked {

    fun onItemClicked(soundId: Int)
}

