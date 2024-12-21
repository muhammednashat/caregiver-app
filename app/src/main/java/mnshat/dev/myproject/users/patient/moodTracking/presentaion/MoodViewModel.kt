package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mnshat.dev.myproject.users.patient.moodTracking.domain.useCase.GetEmojisStatusUseCase
import javax.inject.Inject


@HiltViewModel
class MoodViewModel @Inject constructor
    (private val getEmojisStatusUseCase: GetEmojisStatusUseCase):ViewModel(){


        fun getEmojisStatus(context: Context) = getEmojisStatusUseCase(context)

}