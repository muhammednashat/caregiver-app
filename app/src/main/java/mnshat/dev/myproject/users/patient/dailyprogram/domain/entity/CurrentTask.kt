package mnshat.dev.myproject.users.patient.dailyprogram.domain.entity

import mnshat.dev.myproject.model.StatusDailyProgram

data class CurrentTask(
    val email:String?= null,
    var dayTask:DayTaskEntity? = null,
    var status: StatusDailyProgram? = null,
){


}