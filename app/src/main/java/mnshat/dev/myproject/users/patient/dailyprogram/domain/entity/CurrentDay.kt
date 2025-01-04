package mnshat.dev.myproject.users.patient.dailyprogram.domain.entity


data class CurrentDay(
    val email:String?= null,
    var dayTask:DayTaskEntity? = null,
    var status: StatusDailyProgram? = null,
){


}