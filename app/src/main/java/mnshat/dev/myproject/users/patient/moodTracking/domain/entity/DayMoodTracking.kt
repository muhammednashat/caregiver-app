package mnshat.dev.myproject.users.patient.moodTracking.domain.entity


data class DayMoodTracking (
    val preMoodIndex: Int? = 0,
    val postMoodIndex: Int? = 0,
    val extraReasons: String? = null,
    val reasons: List<Int>? = null,
    val day: Int? = null,
    val date:Long?  = System.currentTimeMillis(),
    ){
    constructor():this(null, null, null, null, null, null)
}
