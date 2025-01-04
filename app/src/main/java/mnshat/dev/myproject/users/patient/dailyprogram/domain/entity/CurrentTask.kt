package mnshat.dev.myproject.users.patient.dailyprogram.domain.entity


data class CurrentTask(
    val email:String?= null,
    var dayTask:DayTaskEntity? = null,
    var status: StatusDailyProgram? = null,
){


}