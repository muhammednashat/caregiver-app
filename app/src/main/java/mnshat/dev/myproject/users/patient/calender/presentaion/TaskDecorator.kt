package mnshat.dev.myproject.users.patient.calender.presentaion

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import mnshat.dev.myproject.util.log

class TaskDecorator (private  val dates: HashSet<CalendarDay>):DayViewDecorator {

    private val currentDay = CalendarDay.today()
    private var _day :CalendarDay? = null
    override fun shouldDecorate(day: CalendarDay? ):Boolean{
            _day = day
            return  day == currentDay ||  dates.contains(day)
        }

    override fun decorate(view: DayViewFacade?) {
            view.let {
                log("23")
                it?.addSpan(ForegroundColorSpan(Color.RED))
                _day?.let {
                    if (_day == currentDay){
                        view?.addSpan(ForegroundColorSpan(Color.GREEN))
                    }
                }

            }
        }

    private fun yourHighlightDrawable(): Drawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.OVAL
        drawable.setColor(Color.YELLOW)
        return drawable
    }

}