package mnshat.dev.myproject.users.patient.dailyprogram.domain.entity

data class StatusDailyProgram(

    var preChecked: Boolean? = false,
    var postChecked: Boolean? = false,
    var preMoodIndex: Int? = 0,
    var postMoodIndex: Int? = 0,
    var reasons:List<String>? = null,



    var currentLevel: Int? = 1,
    var day: Int? = 1, // 1
    var remaining: Int? = 3, //4
    var completionRate: Int? = 0, // 0

    var currentIndexEducational: Int? = 0,
    var currentIndexBehavioral: Int? = 0,
    var currentIndexSpiritual: Int? = 0,

    var educational: Int? = 0,
    var behavioral: Int? = 0,
    var spiritual: Int? = 0,




)



