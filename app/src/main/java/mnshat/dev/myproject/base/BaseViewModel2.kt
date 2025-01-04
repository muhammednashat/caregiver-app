package mnshat.dev.myproject.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mnshat.dev.myproject.R
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.CurrentTask2
import mnshat.dev.myproject.model.DayTask
import mnshat.dev.myproject.model.StatusDailyProgram2
import mnshat.dev.myproject.util.AGE_GROUP
import mnshat.dev.myproject.util.CURRENT_TASK
import mnshat.dev.myproject.util.GENDER
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log

open class BaseViewModel2(
    private val sharedPreferences: SharedPreferencesManager,
    application: Application
) : AndroidViewModel(application) {
    var intGender = MutableLiveData<Int?>()
    var strGender = MutableLiveData<String?>()
    var intAge = MutableLiveData<Int?>()
    var strAge = MutableLiveData<String?>()
    val currentLang = MutableLiveData<String>()
    var strDialect = MutableLiveData<String?>()
    var intDialect = MutableLiveData<Int?>()


    fun setGender(int: Int) {
        intGender.value = int
    }

    fun setAge(int: Int) {
        intAge.value = int
    }

    fun setDialect(int: Int) {
        intDialect.value = int
    }

    fun setStrGender(context: Context, sharedPreferences: SharedPreferencesManager, gender: Int?) {
        gender?.let {
            sharedPreferences.storeInt(GENDER, gender)
            when (gender) {
                1 -> {
                    strGender.value = context.getString(R.string.male)
                }

                2 -> {
                    strGender.value = context.getString(R.string.female)
                }
            }
        }
    }

    fun setStrAge(context: Context, sharedPreferences: SharedPreferencesManager, age: Int?) {
        age?.let {
            sharedPreferences.storeInt(AGE_GROUP, age)
            when (age) {
                1 -> {
                    strAge.value = context.getString(R.string.young_adulthood1)
                }

                2 -> {
                    strAge.value = context.getString(R.string.middle_age1)
                }

                3 -> {
                    strAge.value = context.getString(R.string.older)
                }
            }
        }
    }


    fun setFavoriteLange(lang: String) {
        currentLang.value = lang
    }

    fun retrieveTaskDayFromDatabase(
        day: String,
        email: String,
        userId: String,
        sharedPreferences: SharedPreferencesManager,
        callBack: () -> Unit
    ) {
        val db = Firebase.firestore
        db.collection("daily_programs").document(day).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val dayTask = document.toObject(DayTask::class.java)
                    filterBasedOnProfilePreferences(
                        dayTask!!,
                        userId,
                        day,
                        email,
                        sharedPreferences
                    ) { // 6
                        log("retrieveTaskDayFromDatabase  1")

                        callBack() // 7
                    }
                    log("retrieveTaskDayFromDatabase 2")

                } else {
                    callBack()
                }
                log("retrieveTaskDayFromDatabase 4")

            }
            .addOnFailureListener { exception ->
                log("retrieveTaskDayFromDatabase 5")

                callBack()
            }
    }

    private fun filterBasedOnProfilePreferences(
        dayTask: DayTask,
        userId: String,
        day: String,
        email: String,
        sharedPreferences: SharedPreferencesManager,
        callBack: () -> Unit
    ) {
       val statusDailyProgram =  StatusDailyProgram2(day = day.toInt())
        val isReligious = sharedPreferences.getBoolean(RELIGION)
        if (!isReligious) {
            dayTask.spiritual = null
            statusDailyProgram.remaining = 2
        }


        storeCurrentTaskRemotely(dayTask, userId, email, sharedPreferences,statusDailyProgram) {

            callBack()  //5
        }


    }

    private fun storeCurrentTaskRemotely(
        dayTask: DayTask,
        userId: String,
        email: String,
        sharedPreferences: SharedPreferencesManager,
        statusDailyProgram:StatusDailyProgram2,
        callBack: () -> Unit

    ) {
        FirebaseService.storeCurrentTaskRemotely(
            userId,
            CurrentTask2(email, dayTask,statusDailyProgram)
        ) {
            it?.let {
                storeCurrentTaskLocally(it, sharedPreferences) { // 2
                    callBack() // 3
                }
            }
        }

    }

    fun storeCurrentTaskLocally(
        currentTask: CurrentTask2,
        sharedPreferences: SharedPreferencesManager,
        callBack: () -> Unit
    ) {
        log("storeCurrentTaskLocally ")
        sharedPreferences.storeObject(CURRENT_TASK, currentTask)
        callBack()  // 1
    }


}