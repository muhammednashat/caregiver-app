package mnshat.dev.myproject.users.patient.supporters.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.users.patient.supporters.entity.Partner
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USERS
import mnshat.dev.myproject.util.log

class SupportersRepo (
    private val firestore: FirebaseFirestore,
    private val sharedPreferences: SharedPreferencesManager
) {


    fun userProfile () = sharedPreferences.getUserProfile()

    suspend fun retrieveSupporters(userId: String) {
        log("retrieveSupporters")
    val snapShot = firestore.collection(USERS).document(userId).collection("Partners").get().await()
       for (document in snapShot){
       val partner  = document.toObject(Partner::class.java)
        log(partner.id!!)
     }
    }
}