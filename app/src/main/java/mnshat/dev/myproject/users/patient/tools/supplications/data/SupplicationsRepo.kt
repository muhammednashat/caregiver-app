package mnshat.dev.myproject.users.patient.tools.supplications.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.R
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.SUPPLICATIONS
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USERS
import mnshat.dev.myproject.util.data.getListHands
import mnshat.dev.myproject.util.data.getListSebha
import mnshat.dev.myproject.util.log

class SupplicationsRepo (
     val firestore: FirebaseFirestore,
     val sharedPreferences: SharedPreferencesManager
) {

     fun getUser() = sharedPreferences.getUserProfile()

     suspend fun storeUserSupplication(newSupplication: Supplication) {

        firestore.collection(USERS)
               .document(getUser().id!!)
               .collection(SUPPLICATIONS)
               .add(newSupplication).await()
         log("Done Successfully")
     }


    fun listenToUserSupplications(onChange: (List<Supplication>) -> Unit) {
        firestore.collection(USERS)
            .document(getUser().id!!)
            .collection(SUPPLICATIONS)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    log("Listen failed: ${e.message}")
                    onChange(emptyList())
                    return@addSnapshotListener
                }
                val supplications = snapshots?.documents
                    ?.mapNotNull { it.toObject(Supplication::class.java) } ?: emptyList()
                log("Real-time update: ${supplications.size} items")
                onChange(supplications)
            }
    }

    fun listenToAppSupplications(onChange: (List<Supplication>) -> Unit) {
        firestore
            .collection(SUPPLICATIONS)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    log("Listen failed: ${e.message}")
                    onChange(emptyList())
                    return@addSnapshotListener
                }
                val supplications = snapshots?.documents
                    ?.mapNotNull { it.toObject(Supplication::class.java) } ?: emptyList()
                log("Real-time update: ${supplications.size} items")
                onChange(supplications)
            }
    }



    fun addSupplications() {
        val supplications = listOf(
            Supplication(
                name = "الحمد لله", number = 33
            ),
            Supplication(
                name = "سبحان الله", number = 33
            ),
            Supplication(
                name = "الله اكبر", number = 33
            ),
            Supplication(
                name = "لا اله إلا الله", number = 33
            ),
        )

        val collection = firestore.collection(SUPPLICATIONS)
        supplications.forEach {
            collection.add(it)
        }
    }

    fun handsList()= getListHands()

    fun sebhaList()= getListSebha()




}