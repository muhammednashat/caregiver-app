package mnshat.dev.myproject.users.patient.calender.domain

import android.content.Context
import mnshat.dev.myproject.users.patient.calender.data.CalenderActivitiesRepo
import javax.inject.Inject

class GetCalenderActivitiesUseCase @Inject constructor(val  calenderActivitiesRepository: CalenderActivitiesRepo){

//    private  val calenderActivitiesRepositoryf : CalenderActivitiesRepo
//    private  val calenderActivitiesRepositoryf : CalenderActivitiesRepo

    operator fun invoke(context: Context) =

        calenderActivitiesRepository.getCalenderActivities(context)

}

class  Wat(){

    val calenderActivitiesRepository = CalenderActivitiesRepo()

    val get = GetCalenderActivitiesUseCase(calenderActivitiesRepository)
}