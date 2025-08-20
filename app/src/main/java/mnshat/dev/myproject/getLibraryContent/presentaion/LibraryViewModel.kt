package mnshat.dev.myproject.getLibraryContent.presentaion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.getLibraryContent.data.LibraryContentRepo
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.model.Posts
import mnshat.dev.myproject.util.COMMON_CONTENT
import mnshat.dev.myproject.util.POSTS
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USER_EMAIL
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val libraryContentRepo: LibraryContentRepo,
    val sharedPreferences: SharedPreferencesManager,

    ) : ViewModel() {

    private val _isReadyDisplay = MutableLiveData<Boolean>()
    val isReadyDisplay: LiveData<Boolean> = _isReadyDisplay

    private var currentContentIndex = 0
    private var currentContent = ""

    lateinit var mLibraryContentCustomized: List<LibraryContent>

    lateinit var mLibraryContentMostCommon: List<LibraryContent>

    fun retrieveLibraryContent() {
        viewModelScope.launch {
          try {
              val  data = libraryContentRepo.getLibraryContent()
             if (data.isNotEmpty()) {
            setLibraryContentMostCommon(data)
            setLibraryContentCustomized(data)
            _isReadyDisplay.value = true
        }else{

        }
          }catch (e:Exception){

          }
        }

    }

    fun getContent(): LibraryContent {
        val list = getCurrentContents()
        val content = list[currentContentIndex]
        return content
    }


    private fun getCurrentContents(): List<LibraryContent> {
        return when (currentContent) {
            COMMON_CONTENT -> mLibraryContentMostCommon
            else -> mLibraryContentCustomized
        }
    }

    fun getContentsBasedType(type: String) =
        getCurrentContents().filter { it.type == type }


    private fun libraryContentsBasedReligion(libraryContents: List<LibraryContent>?): List<LibraryContent> {
        val isReligion = sharedPreferences.getBoolean(RELIGION)
        return if (isReligion) libraryContents!!
        else libraryContents!!.filter { it.religion == false }
    }


    private fun setLibraryContentMostCommon(libraryContents: List<LibraryContent>?) {
        mLibraryContentMostCommon = libraryContents?.sortedByDescending { it.viewCount }?.take(4)!!
    }

    private fun setLibraryContentCustomized(libraryContents: List<LibraryContent>?) {
        mLibraryContentCustomized = libraryContentsBasedReligion(libraryContents)
    }

    fun resetIsReadyDisplay() {
        _isReadyDisplay.value = false
    }

    fun setCurrentContentIndex(index: Int) {
        currentContentIndex = index
    }


    fun setCurrentContentContent(content: String) {
        currentContent = content
    }

    fun shareContent(post: Post, callback: (String?) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection(POSTS)
            .document(sharedPreferences.getString(USER_EMAIL)).get().addOnSuccessListener {
                val posts: MutableList<Post> =
                    if (it.exists()) {
                        it.toObject(Posts::class.java)?.posts ?: mutableListOf()
                    } else {
                        mutableListOf()
                    }
                posts.add(post)
                FirebaseFirestore.getInstance()
                    .collection(POSTS)
                    .document(sharedPreferences.getString(USER_EMAIL))
                    .set(Posts(posts)).addOnCompleteListener {
                        callback(null)
                    }.addOnFailureListener {
                        callback(it.message)
                    }

            }.addOnFailureListener {
                callback(it.message)
            }
    }



//    fun getLibraryContent(onDataFetched: (List<LibraryContent>?) -> Unit) {
//
//        libraryContents.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val libraryContentList = mutableListOf<LibraryContent>()
//                for (contentSnapshot in snapshot.children) {
//                    val libraryContent = contentSnapshot.getValue(LibraryContent::class.java)
//                    libraryContent?.let {
//                        libraryContentList.add(it)
//                    }
//                }
//                onDataFetched(libraryContentList)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                onDataFetched(null)
//                println("Failed to retrieve data: ${error.message}")
//            }
//        })
//
//    }

}