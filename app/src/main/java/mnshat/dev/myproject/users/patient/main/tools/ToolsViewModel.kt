package mnshat.dev.myproject.users.patient.main.tools

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mnshat.dev.myproject.base.BaseViewModel
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log

class ToolsViewModel(private val sharedPreferences: SharedPreferencesManager,
                     application: Application
):BaseViewModel(sharedPreferences,application) {




    private val _suggestedSupplication = MutableLiveData<List<Supplication>>()
    val suggestedSupplication: LiveData<List<Supplication>>
        get() = _suggestedSupplication


    private val _userSupplication = MutableLiveData<List<Supplication>>()
    val userSupplication: LiveData<List<Supplication>>
        get() = _userSupplication

    init {
        _suggestedSupplication.value = supplications
        _userSupplication.value = supplications
    }

    fun getUserSupplication()= supplications

    fun onAddSupplicationClick(instanceSupplication: Supplication) {
        val firestore = Firebase.firestore
        val supplicationsUsers = firestore.collection("supplications_users")
        // Add the supplication instance to the collection
        supplicationsUsers.add(instanceSupplication)
            .addOnSuccessListener { documentReference ->
                // Handle successful addition
                // You can show a success message or perform other actions
                println("Supplication added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                // Handle failure
                // You can show an error message or perform other actions
                println("Error adding supplication: $e")
            }
    }


}


val supplications = listOf(
    Supplication("Supplication 1", 10),
    Supplication("Supplication 2",34 ),
    Supplication("Supplication 3", 45),
    Supplication("Supplication 1", 54),
    Supplication("Supplication 2", 5),
    Supplication("Supplication 3", 3)
)