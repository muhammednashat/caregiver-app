package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.DailyProgramManagerUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.DayMoodTracking
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetDayTrackingMoodUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEffectingMoodUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEmojisStatusUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.StoreDayMoodTrackingLocallyUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.StoreDayMoodTrackingRemotelyUseCase
import mnshat.dev.myproject.util.CURRENT_DAY
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.log
import javax.inject.Inject


@HiltViewModel
class MoodViewModel @Inject constructor
    (
    private val getEmojisStatusUseCase: GetEmojisStatusUseCase,
    private val getEffectingMoodUseCase: GetEffectingMoodUseCase,
    private val  dailyProgramManagerUseCase: DailyProgramManagerUseCase,
    val sharedPreferences: SharedPreferencesManager,
    private val storeDayMoodTrackingLocallyUseCase: StoreDayMoodTrackingLocallyUseCase,
    private val  storeDayMoodTrackingRemotelyUseCase: StoreDayMoodTrackingRemotelyUseCase,
    private val getDayTrackingMoodUseCase: GetDayTrackingMoodUseCase,
    ):ViewModel(){
         private var preMoodIndex = -1
         private var postMoodIndex = -1
         private var _emoji: EmojiMood? = null
         fun getEmojisStatus(context: Context)  =  getEmojisStatusUseCase(context)
         fun getEffectingMood(context: Context) = getEffectingMoodUseCase(context)
         fun setEmoji(emoji: EmojiMood)  { _emoji = emoji }
         fun getEmoji() = _emoji

    fun updateCurrentTaskPreMood(list:List<Int>?,text:String?) {
         CoroutineScope(Dispatchers.IO).launch {
            log("viewModelScope.launch updateCurrentTaskPreMood")
            val currentDay = dailyProgramManagerUseCase.getCurrentDayLocallyUseCase()
            currentDay.status?.preChecked = true
            currentDay.status?.preMoodIndex = preMoodIndex
            currentDay.status?.reasons = list
            currentDay.status?.extraReasons = text
            dailyProgramManagerUseCase.updateCurrentDayLocallyUseCase(currentDay)
            dailyProgramManagerUseCase.updateCurrentDayRemotelyUseCase(currentDay)
        }
    }

     fun getCurrentDayLocally(): CurrentDay{
        val string = sharedPreferences.getString(CURRENT_DAY, null.toString())
        val gson = Gson()
        return gson.fromJson(string, CurrentDay::class.java)
    }

    fun updateCurrentDayPostMood() {
        CoroutineScope(Dispatchers.IO).launch {
            val currentTask = dailyProgramManagerUseCase.getCurrentDayLocallyUseCase()

            currentTask.status?.postChecked = true
            currentTask.status?.postMoodIndex = postMoodIndex
            dailyProgramManagerUseCase.updateCurrentDayLocallyUseCase(currentTask)
            dailyProgramManagerUseCase.updateCurrentDayRemotelyUseCase(currentTask)
        }
    }

    fun setPreMoodIndex(index: Int) {
        preMoodIndex = index
    }
    fun setPostMoodIndex(index: Int) {
        postMoodIndex = index
    }
    fun getNextDay(day: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dailyProgramManagerUseCase.getNextDayUseCase(day)
        }
    }

    fun storeDayMoodTracking(currentDayLocally: DayMoodTracking, userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            storeDayMoodTrackingLocallyUseCase(currentDayLocally, userId)
    }
    }

    fun getPostMoodIndex(): Int  = postMoodIndex

     fun getDayMoodTracking() = liveData<List<DayMoodTracking>?> {
        getDayTrackingMoodUseCase(sharedPreferences.getString(USER_ID, "")).collect {
                 emit(it)
        }
    }

}