package mnshat.dev.myproject.users.patient.dailyprogram.domain.entity

import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.DayMoodTracking


data class CurrentDay(
    val email:String?= null,
    var dayTask:DayTaskEntity? = null,
    var status: StatusDailyProgram? = null,
){

    fun toDayMoodTracking(): DayMoodTracking {
      return  DayMoodTracking(
          postMoodIndex = status?.postMoodIndex,
          preMoodIndex = status?.preMoodIndex,
          reasons = status?.reasons,
          extraReasons = status?.extraReasons,
          day = status?.day,
      )

    }

}