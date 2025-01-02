package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.CurrentTask2
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEffectingMoodUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEmojisStatusUseCase
import mnshat.dev.myproject.util.CURRENT_TASK
import mnshat.dev.myproject.util.STATUS
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log
import javax.inject.Inject


@HiltViewModel
class MoodViewModel @Inject constructor
    (
        private val getEmojisStatusUseCase: GetEmojisStatusUseCase,
        private val getEffectingMoodUseCase: GetEffectingMoodUseCase,
        private val sharedPreferences: SharedPreferencesManager
    ):ViewModel(){

        private var _emoji: EmojiMood? = null

        fun getEmojisStatus(context: Context) = getEmojisStatusUseCase(context)
         fun getEffectingMood(context: Context) = getEffectingMoodUseCase(context)
    fun setEmoji(emoji: EmojiMood)  { _emoji = emoji }
    fun getEmoji() = _emoji

     private fun getCurrentTask(): CurrentTask2 {
        val string = sharedPreferences.getString(CURRENT_TASK, null.toString())
        val gson = Gson()
        return gson.fromJson(string, CurrentTask2::class.java)
    }

    fun updateCurrentTaskLocally() {
        val currentTask = getCurrentTask()
        currentTask.status?.preChecked = true
        sharedPreferences.storeObject(CURRENT_TASK, currentTask)
        updateCurrentTaskRemotely(currentTask)
    }

    private fun updateCurrentTaskRemotely(currentTask:CurrentTask2) {
        log("updateCurrentTaskRemotely ${currentTask.status?.preChecked}")
        val map = mapOf(STATUS to currentTask.status!!)
        FirebaseService.updateTasksUser(FirebaseService.userId, map) {
        }
    }



}