package mnshat.dev.myproject.getLibraryContent.presentaion

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
import mnshat.dev.myproject.util.COMMON_CONTENT
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.loadImage

class CommonContentLibraryAdapter(
    private val libraryContents: List<LibraryContent>?,
    private val context: Context,
    private val sharedPreferences: SharedPreferencesManager,
    private val onItemLibraryContentClicked: OnItemLibraryContentClicked
) :
    RecyclerView.Adapter<CommonContentLibraryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_most_common_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // card
        val libraryContent = libraryContents?.get(position)

        loadImage(context, libraryContent?.imageURL, holder.imageView)
        setText(libraryContent,holder.title)

        holder.itemView.setOnClickListener {

            onItemLibraryContentClicked.onItemClicked(
                libraryContent?.type!!,
                position,
                COMMON_CONTENT
            )


        }
    }


    private fun setText(libraryContent: LibraryContent?, title: TextView) {
       if (sharedPreferences.getString(LANGUAGE) ==  "en"){
           title.text = libraryContent?.enTitle
       }else{
           title.text = libraryContent?.arTitle
       }


    }

    override fun getItemCount(): Int {
        return libraryContents?.size!!
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById<TextView>(R.id.title)
        var imageView: ImageView = itemView.findViewById<ImageView>(R.id.imageView)
    }
}