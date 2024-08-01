package mnshat.dev.myproject.users.patient.main.tools.supplications

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.model.SupplicationsUser
import mnshat.dev.myproject.util.EMAIL
import mnshat.dev.myproject.util.SharedPreferencesManager

class SupplicationsViewModel(private val sharedPreferences: SharedPreferencesManager,
                             application: Application
):BaseViewModel(sharedPreferences,application) {

    private val firestore = Firebase.firestore

    //TODo chane the way of getting email
    private var supplicationsUsersDoc =
        firestore.collection("supplications").document(FirebaseService.userEmail!!)

    private val _isDismissProgressDialog = MutableLiveData<Boolean>()
    val isDismissProgressDialog: LiveData<Boolean> get() = _isDismissProgressDialog

    private val _suggestedSupplication = MutableLiveData<List<Supplication>>()
    val suggestedSupplication: LiveData<List<Supplication>>
        get() = _suggestedSupplication

    private val _supplication = MutableLiveData<Supplication>()
    val supplication: LiveData<Supplication>
        get() = _supplication


    private val _userSupplications = MutableLiveData<List<Supplication>>()
    val userSupplications: LiveData<List<Supplication>>
        get() = _userSupplications



    fun onAddSupplicationClick(instanceSupplication: Supplication) {
        supplicationsUsersDoc.get()
            .addOnSuccessListener { documentSnapshot ->
                val supplicationsList: MutableList<Supplication> = if (documentSnapshot.exists()) {
                    documentSnapshot.toObject(SupplicationsUser::class.java)?.supplications
                        ?: mutableListOf()
                } else {
                    mutableListOf()
                }

                supplicationsList.add(instanceSupplication)
                supplicationsUsersDoc.set(SupplicationsUser(supplicationsList))
                    .addOnSuccessListener {
                        println("Supplication added successfully")
                        _isDismissProgressDialog.value = true
                    }
                    .addOnFailureListener { e ->
                        println("Error adding supplication: $e")
                        _isDismissProgressDialog.value = true
                    }
            }
            .addOnFailureListener { e ->
                println("Error retrieving document: $e")
                _isDismissProgressDialog.value = true
            }
    }

    private fun getSupplicationsList(
        document: String,
        onResult: (List<Supplication>, Exception?) -> Unit
    ) {

        val supplicationsUsersDoc = firestore.collection("supplications").document(document)
        supplicationsUsersDoc.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val supplicationsUser = documentSnapshot.toObject(SupplicationsUser::class.java)
                    val supplicationsList = supplicationsUser?.supplications ?: emptyList()
                    onResult(supplicationsList, null)
                    _isDismissProgressDialog.value = true

                } else {
                    onResult(emptyList(), null)
                    _isDismissProgressDialog.value = true

                }
            }
            .addOnFailureListener { exception ->
                onResult(emptyList(), exception)
                _isDismissProgressDialog.value = true
            }
    }
    fun setSupplication(supplication: Supplication){
        _supplication.value = supplication
    }

    fun getSuggestedSupplications(onResult: (List<Supplication>) -> Unit) {
        getSupplicationsList("app"
        ) { items, exception ->
            items.let {
                _suggestedSupplication.value = items
                onResult(items)
            }
        }


    }

    fun getUserSupplications(onResult: (List<Supplication>) -> Unit){
        getSupplicationsList(FirebaseService.userEmail!!
        ) { items, exception ->
            _userSupplications.value = items
            onResult(items)
        }


    }

    fun resetIsDismissProgressDialog() {
        _isDismissProgressDialog.value = false
    }

}


