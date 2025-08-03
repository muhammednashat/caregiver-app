package mnshat.dev.myproject.users.patient.tools.supplications.data

import com.google.firebase.firestore.FirebaseFirestore
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.SUPPLICATIONS
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USERS

class SupplicationsRepo (
     val firestore: FirebaseFirestore,
     val sharedPreferences: SharedPreferencesManager
) {

     fun getUser() = sharedPreferences.getUserProfile()

     fun storeUserSupplication(newSupplication: Supplication)=
           firestore.collection(USERS)
               .document(getUser().id!!)
               .collection(SUPPLICATIONS)
               .add(newSupplication)


}