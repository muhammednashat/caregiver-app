package mnshat.dev.myproject.users.patient.calender.data.repo

import mnshat.dev.myproject.users.patient.calender.data.daos.DayDao
import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity
import javax.inject.Inject

class DayRepository @Inject constructor (val dayDao: DayDao){

     suspend fun addDay(dayEntity: DayEntity) =
      dayDao.addDay(dayEntity)
}