package mnshat.dev.myproject.users.patient.tools.supplications.data

import com.google.firebase.firestore.FirebaseFirestore
import mnshat.dev.myproject.util.SharedPreferencesManager

class SupplicationsRepo (
     val firestore: FirebaseFirestore,
     val sharedPreferences: SharedPreferencesManager
) {

     fun getUser() = sharedPreferences.getUserProfile()

}