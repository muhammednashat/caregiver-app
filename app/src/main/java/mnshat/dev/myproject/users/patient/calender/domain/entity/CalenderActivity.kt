package mnshat.dev.myproject.users.patient.calender.domain.entity

data class CalenderActivity(
    val image: Int,
    val nameTask: String,
    val background: String,
    var religion: Boolean? = false,
    var gender: Int? = null,
    var ageGroup: Int? = null,
    val description: String="",

){

}
