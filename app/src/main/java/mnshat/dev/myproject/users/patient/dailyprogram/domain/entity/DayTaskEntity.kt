package mnshat.dev.myproject.users.patient.dailyprogram.domain.entity


data class DayTaskEntity(
    var spiritual: List<Task2?>? = null,
    val behaviorActivation: List<Task2?>? = null,
    val educational: List<Task2?>? = null
)
