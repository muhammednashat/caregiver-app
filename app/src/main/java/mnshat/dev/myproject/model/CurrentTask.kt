package mnshat.dev.myproject.model
data class CurrentTask(
    val email:String?= null,
    var dayTask:DayTask? = null,
    var status:StatusDailyProgram? = null,
){
//    companion object {
//        fun getClass(): CurrentTask {
//            return CurrentTask( )
//        }
//    }

}