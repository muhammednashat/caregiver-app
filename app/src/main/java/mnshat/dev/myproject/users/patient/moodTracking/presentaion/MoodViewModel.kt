package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.dailyprogram.domain.useCase.DailyProgramManagerUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEffectingMoodUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEmojisStatusUseCase
import javax.inject.Inject


@HiltViewModel
class MoodViewModel @Inject constructor
    (
        private val getEmojisStatusUseCase: GetEmojisStatusUseCase,
        private val getEffectingMoodUseCase: GetEffectingMoodUseCase,
        private val  dailyProgramManagerUseCase: DailyProgramManagerUseCase,
    ):ViewModel(){
         private var preMoodIndex = -1
         private var postMoodIndex = -1

         private var _currentDay= MutableLiveData<CurrentDay>()
            val currentDay: LiveData<CurrentDay> = _currentDay

         private var _emoji: EmojiMood? = null
         fun getEmojisStatus(context: Context)  =  getEmojisStatusUseCase(context)
         fun getEffectingMood(context: Context) = getEffectingMoodUseCase(context)
         fun setEmoji(emoji: EmojiMood)  { _emoji = emoji }
         fun getEmoji() = _emoji

    fun updateCurrentTaskPreMood() {
        viewModelScope.launch {
            val currentTask = dailyProgramManagerUseCase.getCurrentDayLocallyUseCase()
            currentTask.status?.preChecked = true
            currentTask.status?.preMoodIndex = preMoodIndex
            currentTask.status?.reasons = listOf("qw","2ti3","jhh")
            dailyProgramManagerUseCase.updateCurrentDayLocallyUseCase(currentTask)
            dailyProgramManagerUseCase.updateCurrentDayRemotelyUseCase(currentTask)
        }
    }
    fun getCurrentDay(){
        viewModelScope.launch {
        _currentDay.value =  dailyProgramManagerUseCase.getCurrentDayLocallyUseCase()
        }
    }

    fun updateCurrentDayPostMood() {
        viewModelScope.launch {
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

}