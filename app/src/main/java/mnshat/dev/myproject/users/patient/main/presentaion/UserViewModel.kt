package mnshat.dev.myproject.users.patient.main.presentaion

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.main.data.UserDataRepository
import mnshat.dev.myproject.util.log
import javax.inject.Inject
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
@HiltViewModel
 class UserViewModel @Inject constructor(
     private  val userDataRepository: UserDataRepository,
      val firebaseAnalytics: FirebaseAnalytics
) : ViewModel() {


    val userProfile = userDataRepository.userProfile()


    fun currentTask() : CurrentDay {
        val day = userDataRepository.getCurrentDayLocally()
        log("day = $day")
        return day


    }

    fun logEvent(){
      val d =   firebaseAnalytics.logEvent("daily_program_clicked") {
            param(FirebaseAnalytics.Param.ITEM_ID, "id")
        }
        log("done = $d")
    }

    fun updateFirstTimeState() {
        userDataRepository.updateLoggedStatus()
    }

    fun isFirstTime ()= userDataRepository.isFirstTime()



}

