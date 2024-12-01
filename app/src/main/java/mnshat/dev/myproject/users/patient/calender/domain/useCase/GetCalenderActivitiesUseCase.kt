package mnshat.dev.myproject.users.patient.calender.domain.useCase

import android.content.Context
import mnshat.dev.myproject.users.patient.calender.data.repo.CalenderActivitiesRepo
import javax.inject.Inject

class GetCalenderActivitiesUseCase @Inject constructor(val  calenderActivitiesRepository: CalenderActivitiesRepo){

    operator fun invoke(context: Context) =

        calenderActivitiesRepository.getCalenderActivities(context)

}

