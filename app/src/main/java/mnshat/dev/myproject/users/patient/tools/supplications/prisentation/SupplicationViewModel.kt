package mnshat.dev.myproject.users.patient.tools.supplications.prisentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.model.Posts
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.model.SupplicationsUser
import mnshat.dev.myproject.users.patient.tools.supplications.data.SupplicationsRepo
import mnshat.dev.myproject.util.POSTS
import mnshat.dev.myproject.util.data.getListHands
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@HiltViewModel
class SupplicationViewModel @Inject constructor(

    val supplicationsRepo: SupplicationsRepo,

):ViewModel() {



    private val _isDismissProgressDialog = MutableLiveData<Boolean>()
    val isDismissProgressDialog: LiveData<Boolean> get() = _isDismissProgressDialog
    private val _dismissSupplicationDialog = MutableLiveData<Boolean>()
    val dismissSupplicationDialog: LiveData<Boolean> get() = _dismissSupplicationDialog
    private val firestore = Firebase.firestore
    val user = supplicationsRepo.getUser()





    private val _suggestedSupplication = MutableLiveData<List<Supplication>>()
    val suggestedSupplication: LiveData<List<Supplication>>
        get() = _suggestedSupplication


    private val _supplication = MutableLiveData<Supplication>()
    val supplication: LiveData<Supplication>
        get() = _supplication





    private val _userSupplications = MutableLiveData<List<Supplication>>()
    val userSupplications: LiveData<List<Supplication>>
        get() = _userSupplications


    init {
        supplicationsRepo.listenToUserSupplications(){
            _userSupplications.value = it
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



    fun getSuggestedSupplications(onResult: (List<Supplication>) -> Unit) {
        getSupplicationsList("app"
        ) { items, exception ->
            items.let {
                _suggestedSupplication.value = items
                onResult(items)
            }
        }
    }





    fun resetIsDismissProgressDialog() {
        _isDismissProgressDialog.value = false
    }
   fun resetDismissSupplicationDialog() {
     _dismissSupplicationDialog.value = false
    }

    fun storeUserSupplication(newSupplication: Supplication){
        viewModelScope.launch {
            try {
                 supplicationsRepo.storeUserSupplication(newSupplication)
                _dismissSupplicationDialog.value = true
            }catch (e:Exception){
                _dismissSupplicationDialog.value = false
            }
        }

    }


}
