package mnshat.dev.myproject.users.patient.calender.data.repo

import mnshat.dev.myproject.users.patient.calender.data.daos.DayDao
import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity
import mnshat.dev.myproject.users.patient.calender.domain.repo.DayRepository

class DayRepositoryImp (private val dayDao: DayDao): DayRepository {

    override suspend fun addDay(dayEntity: DayEntity) =
      dayDao.addDay(dayEntity)
}