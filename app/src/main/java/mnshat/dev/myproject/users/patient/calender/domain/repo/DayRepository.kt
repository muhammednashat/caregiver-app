package mnshat.dev.myproject.users.patient.calender.domain.repo

import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity

interface DayRepository {

    suspend fun addDay(dayEntity: DayEntity):Long
}