package mnshat.dev.myproject.users.patient.calender.presentaion

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import android.graphics.Color
import android.text.style.ForegroundColorSpan

class TaskDecorator (private  val dates: HashSet<CalendarDay>):DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?) =
        dates.contains(day)

    override fun decorate(view: DayViewFacade?) {
       view.let {
           it?.addSpan(ForegroundColorSpan(Color.RED))
       }
    }
}