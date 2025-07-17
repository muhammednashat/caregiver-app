package mnshat.dev.myproject.users.patient.profile.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mnshat.dev.myproject.auth.data.entity.UserProfile
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.GetCurrentDayLocallyUseCase
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.GetNextDayUseCase
import mnshat.dev.myproject.users.patient.profile.data.ProfileRepo
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val sharedPreferences: SharedPreferencesManager,
                                           private val profileRepo: ProfileRepo,
                                           private val getCurrentDayLocallyUseCase: GetCurrentDayLocallyUseCase,
                                           private val getNextDayUseCase: GetNextDayUseCase
)
    : ViewModel() {



    val userProfile: UserProfile = profileRepo.userProfile()



    fun resetCurrentDay() {

//        CoroutineScope(Dispatchers.IO).launch {
//
//            log(
//                getNextDayUseCase(getCurrentDayLocallyUseCase().status?.day!!).email
//                    .toString())}

    }


}