package mnshat.dev.myproject.users.patient.calender.data.repo

import android.content.Context
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.calender.domain.entity.CalenderActivity
import mnshat.dev.myproject.util.GENDER
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log
import javax.inject.Inject

class CalenderActivitiesRepo @Inject constructor(private val sharedPreferences: SharedPreferencesManager) {

    fun getCalenderActivities(context: Context) : List<CalenderActivity>{

        val activities = listOf(

            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_1),
                "#4467c4",
                gender = 2
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_2),
                "#4467c4",
                gender = 2
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_3),
                "#4467c4",
                gender = 1
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_4),
                "#4467c4"
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_5),
                "#4467c4",
                gender = 1
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_6),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_7),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_8),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_9),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_10),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_11),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_12),
                "#4467c4",
                gender = 1
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_13),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_14),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_15),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_16),
                "#4467c4"
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_17),
                "#4467c4",
                gender = 2
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_18),
                "#4467c4",
                gender = 2
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_19),
                "#4467c4",
                gender = 2
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_20),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_21),
                "#4467c4",
                gender = 2
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_22),
                "#4467c4",
                gender = 2
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_23),
                "#4467c4",
                gender = 2
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_24),
                "#4467c4",
                gender = 2
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_25),
                "#4467c4",
                gender = 2
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_26),
                "#4467c4",
                gender = 2
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_27),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_28),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_29),
                "#4467c4",
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_30),
                "#4467c4",
                true
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_31),
                "#4467c4",
                true
            ),
            CalenderActivity(
                R.drawable.ic_book1,
                context.getString(R.string.activity_32),
                "#4467c4",
                true
            ),
            CalenderActivity(R.drawable.ic_plan_day, context.getString(R.string.special_activity) ,"#4467c4"),
        )

      return   filterBasedProfile(activities)
    }

    private fun filterBasedProfile(activities: List<CalenderActivity>): List<CalenderActivity>{
        val isReligious = sharedPreferences.getBoolean(RELIGION)
        val gender = sharedPreferences.getInt(GENDER)
        log("$isReligious")
        var activities =
            filterBasedReligion(isReligious, activities)
        log("Size is ->  ${activities.size}")
        activities = filterBasedGender(activities, gender)
        return  activities
    }

    private fun filterBasedReligion(
        isReligious: Boolean,
        activities: List<CalenderActivity>
    ) = if (isReligious) {
        activities.filter { it.religion == true }
    } else {
        activities.filter { it.religion == false }
    }

    private fun filterBasedGender(
        activities: List<CalenderActivity>,
        gender: Int
    ) = when (gender) {
        1 -> {
            activities.filter { it.gender == null || it.gender == 1 }
        }
        2 -> {
            activities.filter { it.gender == null || it.gender == 2 }
        }
        else -> {
            activities
        }
    }


}