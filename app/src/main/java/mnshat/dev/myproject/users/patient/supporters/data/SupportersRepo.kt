package mnshat.dev.myproject.users.patient.supporters.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.auth.data.entity.UserProfile
import mnshat.dev.myproject.users.patient.supporters.entity.Partner
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USERS
import mnshat.dev.myproject.util.log

class SupportersRepo (
    private val firestore: FirebaseFirestore,
    private val sharedPreferences: SharedPreferencesManager
) {

    val supportersProfile = MutableLiveData<List<UserProfile>?>()

    fun userProfile () = sharedPreferences.getUserProfile()

    suspend fun retrieveSupportersIds(userId: String) {
        try {
            val snapShot =
                firestore.collection(USERS).document(userId).collection("Partners").get().await()
            val supportersIds = mutableListOf<String>()

            for (document in snapShot) {
                val partner = document.toObject(Partner::class.java)
                supportersIds.add(partner.id!!)
            }
            retrieveSupporters(supportersIds)
        } catch (e: Exception) {
            supportersProfile.value = null
        }
    }

    private suspend fun retrieveSupporters(supportersIds: List<String>) {
        try {
            val supporters = mutableListOf<UserProfile>()
            val snapshotQuery =
                firestore.collection(USERS).whereIn("id", supportersIds).get().await()
            for (document in snapshotQuery) {
                supporters.add(document.toObject(UserProfile::class.java))
            }
            supportersProfile.value = supporters

        } catch (e: Exception) {
            supportersProfile.value = null
        }

    }



}