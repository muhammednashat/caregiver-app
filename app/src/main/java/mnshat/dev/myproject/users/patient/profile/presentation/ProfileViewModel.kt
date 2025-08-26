package mnshat.dev.myproject.users.patient.profile.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.auth.data.entity.UserProfile
import mnshat.dev.myproject.users.patient.profile.data.ProfileRepo
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val sharedPreferences: SharedPreferencesManager,
                                           private val profileRepo: ProfileRepo,
)
    : ViewModel() {



    val userProfile: UserProfile = profileRepo.userProfile()



    fun userProfile() = profileRepo.userProfile()


    fun resetCurrentDay() {


    }

    fun uploadImageToFireStorage(uri: Uri) {
        viewModelScope.launch {
            try {
                profileRepo.uploadImageToFireStorage(uri)
                //            if(resaul){
//                sharedPreferences.storeString(USER_IMAGE,imageUrl)
//                showToast("تم تغير الصورة بنجاح")
//                binding.updateImage.text = getString(R.string.edit_profile_picture)
//                isPicked = false
//            }else{
//                showToast("خظأ اثناء حفظ الصورة/n رجاءا المحاولة فى وقت لاحق")

//            }
//            dismissProgressDialog()
//        }
            }catch (e: Exception){

            }
        }


    }


}