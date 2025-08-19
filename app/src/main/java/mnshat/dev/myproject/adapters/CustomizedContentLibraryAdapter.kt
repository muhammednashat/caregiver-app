package mnshat.dev.myproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.Customized_CONTENT
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.VIDEO
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log


class CustomizedContentLibraryAdapter(
    private val libraryContents: List<LibraryContent>?,
    private val context: Context,
    private val sharedPreferences: SharedPreferencesManager,
    private val onItemLibraryContentClicked: OnItemLibraryContentClicked
) :
    RecyclerView.Adapter<CustomizedContentLibraryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_customized_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val libraryContent = libraryContents?.get(position)

        loadImage(context, libraryContent?.imageURL, holder.imageView)
        holder.date.text = libraryContent?.date
        holder.type.text = getTextType(libraryContent?.type)
        holder.title.text = getTextTitle(libraryContent)
        setTextDuration(libraryContent, holder.duration)

        holder.itemView.setOnClickListener {
            log("adapter clicked")

            onItemLibraryContentClicked.onItemClicked(
                libraryContent?.type!!,
                position,
                Customized_CONTENT
            )
        }
    }

    private fun setTextDuration(libraryContent: LibraryContent?, duration: TextView) {
        if (libraryContent?.type == ARTICLE) {
            duration.visibility = View.GONE
        } else {
           duration.text = libraryContent?.duration
        }
    }

    private fun getTextType(contentType: String?): String {
        return when (contentType) {
            ARTICLE -> context.getString(R.string.article)
            VIDEO -> context.getString(R.string.video)
            else -> context.getString(R.string.audio)
        }
    }

    private fun getTextTitle(libraryContent: LibraryContent?): String {
        return if (sharedPreferences.getString(LANGUAGE) == "en") {
            libraryContent?.enTitle!!
        } else {
            libraryContent?.arTitle!!
        }
    }

    override fun getItemCount(): Int {
        return libraryContents?.size!!
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById<TextView>(R.id.title)
        var date: TextView = itemView.findViewById<TextView>(R.id.date)
        var type: TextView = itemView.findViewById<TextView>(R.id.type)
        var duration: TextView = itemView.findViewById<TextView>(R.id.duration)
        var imageView: ImageView = itemView.findViewById<ImageView>(R.id.imageView)
    }
}
