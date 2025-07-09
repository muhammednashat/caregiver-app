package mnshat.dev.myproject.users.patient.supporters.data.repos

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.auth.data.entity.UserProfile
import mnshat.dev.myproject.users.patient.supporters.data.entity.Partner
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USERS
import mnshat.dev.myproject.util.USER_PROFILE

class SupportersRepo (
    private val firestore: FirebaseFirestore,
    val sharedPreferences: SharedPreferencesManager
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

    suspend fun storeNewInvitationCode(newInvitationCode: String): Void? {

        val updateData = hashMapOf<String, Any>(
                            "invitationCode" to newInvitationCode,
                            "invitationBase" to newInvitationCode,
                            "invitationUsed" to false,
                        )

        val userRef = firestore.collection(USERS).document(userProfile().id!!)
        return  userRef.update(updateData).await()
    }

    fun updateUserProfileLocal(newInvitationCode: String) {
        val userProfile = userProfile()
        userProfile.invitationCode = newInvitationCode
        userProfile.invitationBase = newInvitationCode
        userProfile.invitationUsed = false
        sharedPreferences.storeObject(USER_PROFILE, userProfile)
    }


}