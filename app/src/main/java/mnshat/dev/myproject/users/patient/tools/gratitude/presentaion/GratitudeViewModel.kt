package mnshat.dev.myproject.users.patient.tools.gratitude.presentaion

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.model.Gratitude
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.users.patient.tools.gratitude.data.GratitudeRepo
import javax.inject.Inject

@HiltViewModel
class GratitudeViewModel @Inject constructor(
    private val  gratitudeRepo: GratitudeRepo,
) : ViewModel() {

    private var _selectedPosition:Int=0
    private var counter :Int = 0

    fun getSelectedPosition() = _selectedPosition

    fun setSelectedPosition(randomNumber: Int) {
        _selectedPosition = randomNumber
    }
    fun sendGratitude(gratitude: Gratitude,callBack:(Boolean) -> Unit) {

//        log(sharedPreferences.getString(USER_EMAIL))
//        FirebaseService.retrieveUser(null, sharedPreferences.getString(USER_EMAIL)) { user ->
//            user?.let{
//                val gratitudeList: MutableList<Gratitude> = user.gratitudeList ?: mutableListOf()
//                gratitudeList.add(gratitude)
//                val map = mapOf<String, Any>("gratitudeList" to gratitudeList)
//                FirebaseService.updateItemsProfileUser(FirebaseService.userId, map) {
//                    if (it) {
//                        callBack(true)
//                        Log.e("TAG" , " 3333333333333")
//                    } else {
//                        callBack(false)
//                        Log.e("TAG" , " 444444444444444")
//                    }
//
//                }
//            }
//
//        }
    }

    fun shareContent(post: Post, callback:(String?)->Unit) {

//        FirebaseFirestore.getInstance()
//            .collection(POSTS)
//            .document(sharedPreferences.getString(USER_EMAIL))
//            .get()
//            .addOnSuccessListener {
//            val posts:MutableList<Post> =
//                if (it.exists()) {
//                    it.toObject(Posts::class.java)?.posts ?: mutableListOf()
//                } else{
//                  mutableListOf()
//                 }
//                posts.add(post)
//                FirebaseFirestore.getInstance()
//                    .collection(POSTS)
//                    .document(sharedPreferences.getString(USER_EMAIL))
//                    .set(Posts(posts)).addOnCompleteListener {
//                        callback(null)
//                    } .addOnFailureListener{
//                        callback(it.message)
//                    }
//
//            }.addOnFailureListener {
//                callback(it.message)
//            }

    }

}