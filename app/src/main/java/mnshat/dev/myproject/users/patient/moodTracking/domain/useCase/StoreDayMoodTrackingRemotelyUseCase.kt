package mnshat.dev.myproject.users.patient.moodTracking.domain.useCase

import mnshat.dev.myproject.users.patient.moodTracking.data.MoodRepository
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.DayMoodTracking
import javax.inject.Inject

class StoreDayMoodTrackingRemotelyUseCase @Inject constructor(private val repository: MoodRepository) {
    suspend operator fun invoke(dayMoodTracking: DayMoodTracking)  =
        repository.storeDayMoodTrackingRemotely(dayMoodTracking)

}