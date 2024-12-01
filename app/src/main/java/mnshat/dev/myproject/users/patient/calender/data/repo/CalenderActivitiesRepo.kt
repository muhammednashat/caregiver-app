package mnshat.dev.myproject.users.patient.calender.data.repo

import android.content.Context
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.calender.domain.entity.CalenderActivity

class CalenderActivitiesRepo {

    fun getCalenderActivities(context: Context) = listOf(
        CalenderActivity(R.drawable.ic_notes, context.getString(R.string.writing_short_story) , "#659bdf"),
        CalenderActivity(R.drawable.ic_tea, context.getString(R.string.drink_a_cup_of_coffee_or_tea), "#7eaef5"),
        CalenderActivity(R.drawable.ic_sun, context.getString(R.string.exposure_to_sunlight) ,"#5675c9"),
        CalenderActivity(R.drawable.ic_cat, context.getString(R.string.spend_time_with_a_pet) ,"#659bdf"),
        CalenderActivity(R.drawable.ic_plant, context.getString(R.string.plant_care) ,"#87cefb"),
        CalenderActivity(R.drawable.ic_book1, context.getString(R.string.reading_a_book) ,"#4467c4"),
    )
}