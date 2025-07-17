package mnshat.dev.myproject.users.patient.moodTracking.domain.useCase

import android.content.Context
import mnshat.dev.myproject.users.patient.moodTracking.data.MoodTrackingRepository
import javax.inject.Inject

class GetEffectingMoodUseCase @Inject constructor(private val moodTrackingRepository: MoodTrackingRepository)  {


    operator  fun  invoke(context: Context) = moodTrackingRepository.getEffectingMood(context)
}