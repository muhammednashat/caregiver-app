package mnshat.dev.myproject.commonFeatures.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.model.Posts
import mnshat.dev.myproject.users.patient.supporters.data.repos.SupportersRepo
import mnshat.dev.myproject.util.POSTS
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor (
    private val supportersRepo: SupportersRepo,
    private val postsRepo: PostsRepo
) : ViewModel() {


  private val _posts = MutableLiveData<List<Post>?>()
  val posts : LiveData<List<Post>?>
      get() = _posts

    val supportersProfile = supportersRepo.supportersProfile

    private val _statusSharing = MutableLiveData<Boolean>()
    val statusSharing: LiveData<Boolean> get() = _statusSharing


    fun  sharePost( post: Post){
        try {
            viewModelScope.launch {
                postsRepo.shareContent(post)
                _statusSharing.value = true
            }
        }catch (e:Exception){
            _statusSharing.value = false

        }
    }

    fun retrieveSupporters(){
        viewModelScope.launch {
            supportersRepo.retrieveSupportersIds(supportersRepo.userProfile().id!!)
        }
    }



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