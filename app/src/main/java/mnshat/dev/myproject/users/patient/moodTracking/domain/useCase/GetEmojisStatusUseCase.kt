package mnshat.dev.myproject.users.patient.moodTracking.domain.useCase

import android.content.Context
import mnshat.dev.myproject.users.patient.moodTracking.data.MoodRepository
import javax.inject.Inject

class GetEmojisStatusUseCase @Inject constructor(private val moodRepository: MoodRepository){


    operator fun invoke(context: Context) = moodRepository.getEmojisStatus(context)

}