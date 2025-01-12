package mnshat.dev.myproject.users.patient.moodTracking.domain.entity

import java.sql.Timestamp


data class DayMoodTracking(
    val preMoodIndex: Int? = 0,
    val postMoodIndex: Int? = 0,
    val extraReasons: String? = null,
    val reasons: List<Int>? = null,
    val day: Int?,
    val date: Timestamp = Timestamp(System.currentTimeMillis()),
    )
