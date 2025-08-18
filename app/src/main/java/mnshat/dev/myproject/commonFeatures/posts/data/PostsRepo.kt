package mnshat.dev.myproject.commonFeatures.posts.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.model.Posts
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.POSTS
import mnshat.dev.myproject.util.SharedPreferencesManager

class PostsRepo(
    private val firestore: FirebaseFirestore,
    private val sharedPreferences: SharedPreferencesManager
) {

    fun getUser() = sharedPreferences.getUserProfile()


    suspend fun retrieveSharedList(): Posts? {
        val user = getUser()
        val email = if (user.typeOfUser == CAREGIVER) user.partnerEmail else user.email
        val result = firestore.collection(POSTS).document(email!!).get().await()
        return result.toObject(Posts::class.java)
    }


    suspend fun shareContent(post: Post): Boolean {
        try {
            val result = firestore.collection(POSTS).document(getUser().email!!).get().await()
            val posts: MutableList<Post> =
                if (result.exists()) {
                    result.toObject(Posts::class.java)?.posts ?: mutableListOf()
                } else {
                    mutableListOf()
                }
            posts.add(post)
            firestore.collection(POSTS).document(getUser().email!!).set(Posts(posts)).await()
            return true
        } catch (e: Exception) {
            return false
        }
    }
}