package mnshat.dev.myproject.users.patient.profile.data

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USERS
import mnshat.dev.myproject.util.USER_PROFILE
import mnshat.dev.myproject.util.log

class ProfileRepo(
    private val firestore: FirebaseFirestore,
    private val fireStorage: FirebaseStorage,
    private val sharedPreferences: SharedPreferencesManager
) {

    fun userProfile() = sharedPreferences.getUserProfile()


    suspend fun uploadImageToFireStorage(imageUri: Uri) {
        val storageRef = fireStorage.reference.child("users_images/${userProfile().id}")
        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    firestore.collection(USERS).document(userProfile().id!!).update("imageUser", uri.toString())
                    updateUserProfileLocally("imageUser", uri.toString())
                }
            }.await()

    }

   suspend fun updateUserProfileRemotely(key: String, value: Any) {
        firestore.collection(USERS).document(userProfile().id!!).update(key, value).await()
        log("1")
       // log(lfsda)"
        updateUserProfileLocally(key, value)
        log("3")
   }

    private fun updateUserProfileLocally(key: String, value: Any) {
        var userProfile = userProfile()
        userProfile.updateData(key, value)
        sharedPreferences.storeObject(USER_PROFILE, userProfile)
        log("2")
    }

}