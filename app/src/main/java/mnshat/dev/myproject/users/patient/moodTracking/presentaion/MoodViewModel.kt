package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEffectingMoodUseCase
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEmojisStatusUseCase
import javax.inject.Inject


@HiltViewModel
class MoodViewModel @Inject constructor
    (
        private val getEmojisStatusUseCase: GetEmojisStatusUseCase,
        private val getEffectingMoodUseCase: GetEffectingMoodUseCase
    ):ViewModel(){

        private var _emoji: EmojiMood? = null

        fun getEmojisStatus(context: Context) = getEmojisStatusUseCase(context)
         fun getEffectingMood(context: Context) = getEffectingMoodUseCase(context)
    fun setEmoji(emoji: EmojiMood)  { _emoji = emoji }
    fun getEmoji() = _emoji



}