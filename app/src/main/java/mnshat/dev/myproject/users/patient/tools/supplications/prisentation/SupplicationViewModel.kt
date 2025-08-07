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
import mnshat.dev.myproject.users.patient.supporters.data.repos.SupportersRepo
import mnshat.dev.myproject.users.patient.tools.supplications.data.SupplicationsRepo
import mnshat.dev.myproject.util.POSTS
import mnshat.dev.myproject.util.SUPPLICATIONS
import mnshat.dev.myproject.util.data.getListHands
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@HiltViewModel
class SupplicationViewModel @Inject constructor(

    val supplicationsRepo: SupplicationsRepo,
    private val supportersRepo: SupportersRepo,

):ViewModel() {

    private val _dismissSupplicationDialog = MutableLiveData<Boolean>()
    val dismissSupplicationDialog: LiveData<Boolean> get() = _dismissSupplicationDialog

    private val _statusSharing = MutableLiveData<Boolean>()
    val statusSharing: LiveData<Boolean> get() = _statusSharing


    val user = supplicationsRepo.getUser()
    var selectedSupplication: Supplication? = null
    private val _suggestedSupplication = MutableLiveData<List<Supplication>>()
    val suggestedSupplication: LiveData<List<Supplication>>
        get() = _suggestedSupplication

    val supportersProfile = supportersRepo.supportersProfile

    private val _userSupplications = MutableLiveData<List<Supplication>>()
    val userSupplications: LiveData<List<Supplication>>
        get() = _userSupplications

    private var mListImages = supplicationsRepo.handsList()
    private var currentIndexListImages = 0




    private val _numberRemaining = MutableLiveData<Int>()
    val numberRemaining: LiveData<Int>
        get() = _numberRemaining
    private val _newImageSupplication = MutableLiveData<Int>()
    val newImageSupplication: LiveData<Int>
        get() = _newImageSupplication


    init {
        supplicationsRepo.listenToUserSupplications(){
            _userSupplications.value = it
        }
        supplicationsRepo.listenToAppSupplications{
            _suggestedSupplication.value = it
        }
    }

    fun retrieveSupporters(){
     viewModelScope.launch {
         supportersRepo.retrieveSupportersIds(user.id!!)
     }
    }

    fun setListImage(listImages: List<Int>) {
        mListImages = listImages
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

   private fun post(list: MutableList<String>) =
        Post(
            type =  SUPPLICATIONS,
            supplication = selectedSupplication,
            supporters = list
        )

    fun  shareSupplication(emailsList: MutableList<String>){
        try {
            viewModelScope.launch {
                supplicationsRepo.shareContent(post(emailsList))
                _statusSharing.value = true
            }
        }catch (e:Exception){
            _statusSharing.value = false

        }
    }




    private fun getImage() {
        if (currentIndexListImages == mListImages.size - 1) {
            currentIndexListImages = 0
        }

        currentIndexListImages++
        _newImageSupplication.value = mListImages[currentIndexListImages]
        _numberRemaining.value = _numberRemaining.value?.plus(1)

    }
    fun onHandClick() {
        log("onHandClick")
        log(_numberRemaining.value.toString())

        if (selectedSupplication?.number == 0) {
            getImage()
        }
        else {
            if (_numberRemaining.value!! < selectedSupplication?.number!!) {
                getImage()
            }
        }

    }
    fun resetCounter(){
        _numberRemaining.value = 0
        currentIndexListImages = 0
        getFirstImage()
    }
    private fun getFirstImage() {
        _newImageSupplication.value = mListImages[currentIndexListImages]
    }
}
