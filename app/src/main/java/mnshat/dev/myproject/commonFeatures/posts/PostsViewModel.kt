package mnshat.dev.myproject.commonFeatures.posts

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.model.Posts
import mnshat.dev.myproject.util.POSTS
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USER_EMAIL
import mnshat.dev.myproject.util.log

class PostsViewModel
    (
    private val sharedPreferences: SharedPreferencesManager,
    application: Application

) : BaseViewModel(
    sharedPreferences,
    application
) {
  private val _posts = MutableLiveData<List<Post>?>()
  val posts : LiveData<List<Post>?>
      get() = _posts

    fun retrieveSharedList() {
        FirebaseFirestore.getInstance()
            .collection(POSTS)
            .document(sharedPreferences.getString(USER_EMAIL)).get().addOnSuccessListener {
                val posts:MutableList<Post> =
                    if (it.exists()) {
                        log("exists")
                        it.toObject(Posts::class.java)?.posts ?: mutableListOf()
                    } else{
                        log("else")
                        mutableListOf()
                    }
                _posts.value = posts
            }.addOnFailureListener {
                _posts.value = emptyList()
                log("addOnFailureListener")
            }

}
}