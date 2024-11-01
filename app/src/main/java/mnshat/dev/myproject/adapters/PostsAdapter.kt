package mnshat.dev.myproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.interfaces.ItemPostsClicked
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.util.GRATITUDE
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.LIBRARY
import mnshat.dev.myproject.util.SUPPLICATIONS
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.getGratitudeQuestionsList


class PostsAdapter(
    private val posts: List<Post>,
    private val context: Context,
    private val sharedPreferences: SharedPreferencesManager,
    private val itemPostsClicked: ItemPostsClicked
) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_post, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val post = posts[position]
     when(post.type){
            GRATITUDE ->  setUpGratitude(holder,post)
            SUPPLICATIONS -> setUpSupplications(holder,post)
            LIBRARY-> setUpLibrary(holder,post)
        }
        holder.display.setOnClickListener {
            itemPostsClicked.onItemClicked(post)
        }
    }

    private fun setUpGratitude(holder: ViewHolder, content: Post) {
        val list = getGratitudeQuestionsList(context)
        holder.text .text = list[content.gratitude!!.index]
        holder.type.text = context.getString(R.string.message_of_gratitude)
        holder.display.text =
            "${context.getString(R.string.display)} ${context.getString(R.string.message_of_gratitude)}"

        holder.imageView.setImageResource(R.drawable.message_gratitude)



    }

    private fun setUpSupplications(holder: ViewHolder, content: Post) {
        holder.text .text = content.supplication?.name
        holder.type.text = context.getString(R.string.supplication_card)
        holder.display.text =
            "${context.getString(R.string.display)} ${context.getString(R.string.supplication_card)}"

        holder.imageView.setImageResource(R.drawable.img_tasbih)

    }

    private fun setUpLibrary(holder: ViewHolder, content: Post) {
        holder.text .text =  if (sharedPreferences.getString(LANGUAGE) == "en") {
            content.libraryContent?.enTitle
        } else {
            content.libraryContent?.arTitle
        }


    }




    override fun getItemCount(): Int {
        return posts.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var type: TextView = itemView.findViewById<TextView>(R.id.type)
        var text: TextView = itemView.findViewById<TextView>(R.id.text)
        var display: TextView = itemView.findViewById<TextView>(R.id.display)
        var imageView: ImageView = itemView.findViewById<ImageView>(R.id.imageView)


    }
}
