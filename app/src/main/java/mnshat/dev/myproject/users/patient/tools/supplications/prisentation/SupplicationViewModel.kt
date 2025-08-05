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

    private val _dismissSupplicationDialog = MutableLiveData<Boolean>()
    val dismissSupplicationDialog: LiveData<Boolean> get() = _dismissSupplicationDialog
    val user = supplicationsRepo.getUser()
    var selectedSupplication: Supplication? = null
    private val _suggestedSupplication = MutableLiveData<List<Supplication>>()
    val suggestedSupplication: LiveData<List<Supplication>>
        get() = _suggestedSupplication

    private val _userSupplications = MutableLiveData<List<Supplication>>()
    val userSupplications: LiveData<List<Supplication>>
        get() = _userSupplications


    init {
        supplicationsRepo.listenToUserSupplications(){
            _userSupplications.value = it
        }
        supplicationsRepo.listenToAppSupplications{
            _suggestedSupplication.value = it
        }
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
    fun onHandClick() {
//        if (supplication.value?.number == 0) {
//            getImage()
//        }
//
//        else {
//            if (_numberRemaining.value!! < supplication.value?.number!!) {
//                getImage()
//            } else {
//
//            }
//        }

    }
    fun resetCounter(){
//        _numberRemaining.value = 0
//        currentIndexListImages = 0
//        getFirstImage()
    }

}
