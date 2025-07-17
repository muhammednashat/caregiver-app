package mnshat.dev.myproject.users.patient.moodTracking.domain.useCase

import mnshat.dev.myproject.users.patient.moodTracking.data.MoodTrackingRepository
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.DayMoodTracking
import javax.inject.Inject

class StoreDayMoodTrackingLocallyUseCase @Inject constructor(private val repository: MoodTrackingRepository) {
    suspend operator fun invoke(dayMoodTracking: DayMoodTracking, userId:String)  =
        repository.storeDayMoodTrackingLocally(dayMoodTracking,userId)

}