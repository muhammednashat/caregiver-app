package mnshat.dev.myproject.users.patient.moodTracking.domain.useCase

import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.moodTracking.data.MoodRepository
import javax.inject.Inject

class StoreDayMoodTrackingRemotelyUseCase @Inject constructor(private val repository: MoodRepository) {
    suspend operator fun invoke(currentDay: CurrentDay)  =
        repository.storeDayMoodTrackingRemotely(currentDay)

}