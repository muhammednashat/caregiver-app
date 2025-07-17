package mnshat.dev.myproject.users.patient.moodTracking.domain.useCase

import mnshat.dev.myproject.users.patient.moodTracking.data.MoodTrackingRepository
import javax.inject.Inject

class GetDayTrackingMoodUseCase @Inject constructor(private val  repository: MoodTrackingRepository) {

     operator fun  invoke(userId: String) = repository.getDayTrackingMood(userId)

}
