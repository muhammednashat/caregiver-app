package mnshat.dev.myproject.commonFeatures.posts

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import mnshat.dev.myproject.base.BaseViewModel2
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.model.Posts
import mnshat.dev.myproject.util.POSTS
import mnshat.dev.myproject.util.SharedPreferencesManager

class PostsViewModel
    (
    private val sharedPreferences: SharedPreferencesManager,
    application: Application

) : BaseViewModel2(
    sharedPreferences,
    application
) {
  private val _posts = MutableLiveData<List<Post>?>()
  val posts : LiveData<List<Post>?>
      get() = _posts

    fun retrieveSharedList(email:String) {
        FirebaseFirestore.getInstance()
            .collection(POSTS)
            .document(email)
            .get()
            .addOnSuccessListener {
                val posts:MutableList<Post> =
                    if (it.exists()) {
                        it.toObject(Posts::class.java)?.posts ?: mutableListOf()
                    } else{
                        mutableListOf()
                    }
                _posts.value = posts
            }.addOnFailureListener {
                _posts.value = emptyList()
            }

}
}