package mnshat.dev.myproject.users.patient.profile.data

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.R
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USERS
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.USER_IMAGE

class ProfileRepo(
    private val firestore: FirebaseFirestore,
    private  val fireStorage: FirebaseStorage,
     private val sharedPreferences: SharedPreferencesManager) {

     fun  userProfile() = sharedPreferences.getUserProfile()





    suspend fun uploadImageToFireStorage(imageUri: Uri) {
        val storageRef = fireStorage.reference.child("users_images/${userProfile().id}")
             storageRef.putFile(imageUri)
                 .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    updateUserProfileRemotely("imageUser",uri.toString())
                }
            }.await()


    }

    private  fun updateUserProfileRemotely(key: String ,value: String) {
        firestore.collection(USERS).document(userProfile().id!!).update(key, value)

    }

    private fun updateUserProfileLocally(key: String, value: Any) {
   sadfsd
    }

}