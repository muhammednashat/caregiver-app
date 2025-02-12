package mnshat.dev.myproject.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mnshat.dev.myproject.dataSource.room.AppDatabase
import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity
import java.util.Date

class MyWorkManager(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        val today = CalendarDay.today()
        val database = AppDatabase.getDatabase(context = context)
        val days = database.dayDao().getAllDays()
        val list = mutableSetOf<CalendarDay>()

        days?.forEach { day ->
            val date = Date(day.day)
            val calendarDay = CalendarDay(date)
            list.add(calendarDay)
        }

        if (list.contains(today)){
            log("yes")
        }else{
            log("no")
        }



        return Result.success()
    }
}


