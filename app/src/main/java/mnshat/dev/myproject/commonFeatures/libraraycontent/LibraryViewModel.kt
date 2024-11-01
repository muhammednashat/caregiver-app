package mnshat.dev.myproject.commonFeatures.libraraycontent

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.model.Posts
import mnshat.dev.myproject.util.COMMON_CONTENT
import mnshat.dev.myproject.util.POSTS
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USER_EMAIL
import mnshat.dev.myproject.util.log

class LibraryViewModel(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application

) : BaseViewModel(
    sharedPreferences,
    application
) {

    private val _isReadyDisplay = MutableLiveData<Boolean>()
    val isReadyDisplay: LiveData<Boolean> = _isReadyDisplay

    private var currentContentIndex = 0
    private var currentContent = ""

    lateinit var mLibraryContentCustomized: List<LibraryContent>

    lateinit var mLibraryContentMostCommon: List<LibraryContent>

    fun retrieveLibraryContent() {

        FirebaseService.getLibraryContent {

            val libraryContents = libraryContentsBasedReligion(it)

            setLibraryContentMostCommon(libraryContents)
            setLibraryContentCustomized(libraryContents)
            _isReadyDisplay.value = true
        }


    }

    fun getContent() :LibraryContent{
        val list =    getCurrentContents()
        val content = list.get(currentContentIndex)
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

    fun shareContent(post: Post, callback:(String?)->Unit) {
        FirebaseFirestore.getInstance()
            .collection(POSTS)
            .document(sharedPreferences.getString(USER_EMAIL)).get().addOnSuccessListener {
                val posts:MutableList<Post> =
                    if (it.exists()) {
                        it.toObject(Posts::class.java)?.posts ?: mutableListOf()
                    } else{
                        mutableListOf()
                    }
                posts.add(post)
                FirebaseFirestore.getInstance()
                    .collection(POSTS)
                    .document(sharedPreferences.getString(USER_EMAIL))
                    .set(Posts(posts)).addOnCompleteListener {
                        callback(null)
                    } .addOnFailureListener{
                        callback(it.message)
                    }

            }.addOnFailureListener {
                callback(it.message)
            }
    }



}