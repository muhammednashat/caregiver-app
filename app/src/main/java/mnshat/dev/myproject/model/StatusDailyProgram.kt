package mnshat.dev.myproject.model

data class StatusDailyProgram(
    var currentLevel: Int? = 1,
    var day: Int? = 1, // 1
    var remaining: Int? = 3, //4
    var completionRate: Int? = 0, // 0
    var currentIndexContemplation: Int? = 0,
    var currentIndexActivity: Int? = 0,
    var currentIndexBehavior: Int? = 0,
    var contemplation: Int? = 0,
    var activity: Int? = 0,
    var behaviorOrSpiritual: Int? = 0,
//    var gratitude: Int? = 0,
//    var currentIndexGratitude: Int? = 0,

)



