package mnshat.dev.myproject.users.patient.profile.data

import com.google.firebase.firestore.FirebaseFirestore
import mnshat.dev.myproject.util.SharedPreferencesManager

class ProfileRepo(
    private val firestore: FirebaseFirestore,
     private val sharedPreferences: SharedPreferencesManager) {
     fun  userProfile() = sharedPreferences.getUserProfile()

}