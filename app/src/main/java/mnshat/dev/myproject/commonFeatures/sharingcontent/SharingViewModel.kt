package mnshat.dev.myproject.commonFeatures.sharingcontent

import android.app.Application
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.SharingContent
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USER_EMAIL
import mnshat.dev.myproject.util.log

class SharingViewModel
    (
    private val sharedPreferences: SharedPreferencesManager,
    application: Application

) : BaseViewModel(
    sharedPreferences,
    application
) {

    fun retrieveSharedList(callback: (List<SharingContent>?, String?) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("sharing")
            .document(sharedPreferences.getString(USER_EMAIL))
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val contentList = document.get("contentList") as? List<SharingContent>
                    if (contentList != null) {
                        callback(contentList, null) // Success: Return the list
                        log("Content list retrieved successfully ${contentList.size}")
                    } else {
                        callback(emptyList(), "Content list is empty") // Handle case where contentList is empty
                        log("Content list is empty")
                    }
                } else {
                    callback(null, "No document found") // Handle case where the document doesn't exist
                    log("No document found")
                }
            }
            .addOnFailureListener { exception ->
                callback(null, exception.toString()) // Handle error
                log("Error retrieving content list: ${exception.message}")
            }
    }


}