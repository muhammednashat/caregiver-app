package mnshat.dev.myproject.interfaces

import android.view.View
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.model.Supplication

interface ItemPostsClicked {

    fun onItemClicked(post: Post)
}