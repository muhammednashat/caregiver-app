package mnshat.dev.myproject.users.patient.moodTracking.presentaion.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mnshat.dev.myproject.users.patient.moodTracking.data.MoodTrackingRepository
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.DayMoodTracking
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood
import mnshat.dev.myproject.util.log
import javax.inject.Inject


@HiltViewModel
class MoodTrackingViewModel @Inject constructor
    (

    private val moodTrackingRepository: MoodTrackingRepository,
    ):ViewModel(){

         private var preMoodIndex = -1
         private var postMoodIndex = -1
         private var _emoji: EmojiMood? = null
         fun currentDay() = moodTrackingRepository.currentDayLocally()

         fun getEmojisStatus(context: Context)  =  moodTrackingRepository.getEmojisStatus(context)
         fun getEffectingMood(context: Context) = moodTrackingRepository.getEffectingMood(context)
         fun setEmoji(emoji: EmojiMood)  { _emoji = emoji }
         fun getEmoji() = _emoji

    fun updateCurrentTaskPreMood(reasons:List<Int>?,extraReasons:String?) {
          val currentDay = currentDay()
          currentDay.status?.preChecked = true
          currentDay.status?.preMoodIndex = preMoodIndex
          currentDay.status?.reasons = reasons
          currentDay.status?.extraReasons = extraReasons
          viewModelScope.launch {
              moodTrackingRepository.dailyProgramRepository.updateCurrentDayLocally(currentDay)
              moodTrackingRepository.dailyProgramRepository.updateCurrentDayRemotely(currentDay)
          log("updateCurrentTaskPreMood")
          }
    }



    fun updateCurrentDayPostMood() {
        val currentDay = currentDay()
        currentDay.status?.postChecked = true
        currentDay.status?.postMoodIndex = postMoodIndex
        viewModelScope.launch {
            moodTrackingRepository.dailyProgramRepository.updateCurrentDayLocally(currentDay)
            moodTrackingRepository.dailyProgramRepository.updateCurrentDayRemotely(currentDay)
        }
    }

    fun setPreMoodIndex(index: Int) {
        preMoodIndex = index
    }
    fun setPostMoodIndex(index: Int) {
        postMoodIndex = index
    }

    fun storeDayMoodTrackingRemotely(){
        viewModelScope.launch {
            moodTrackingRepository.storeDayMoodTrackingRemotely()
        }

    }

    fun getNextDay(day: Int) {
        viewModelScope.launch {
            moodTrackingRepository.dailyProgramRepository.getNextDay(day)
        }

        CoroutineScope(Dispatchers.IO).launch {
//            dailyProgramManagerUseCase.getNextDayUseCase(day)
        }
    }

    fun storeDayMoodTracking(currentDayLocally: DayMoodTracking, userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
//            storeDayMoodTrackingLocallyUseCase(currentDayLocally, userId)
    }
    }

    fun getPostMoodIndex(): Int  = postMoodIndex

     fun getDayMoodTracking() = liveData<List<DayMoodTracking>?> {
//         val userId = if (sharedPreferences.getString(TYPE_OF_USER) == CAREGIVER  ) sharedPreferences.getString(ID_PARTNER, "") else sharedPreferences.getString(USER_ID, "")
//         log(userId)
//        getDayTrackingMoodUseCase(userId).collect {
//                 emit(it)
//        }
    }

}