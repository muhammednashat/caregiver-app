package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Inject

@AndroidEntryPoint
class DailyProgramActivity : AppCompatActivity() {

    private var startTime: Long = 0
   @Inject lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTime = System.currentTimeMillis()
        setContentView(R.layout.activity_daily_program)
    }

    override fun onDestroy() {
        val duration = System.currentTimeMillis() - startTime
        logEvent(duration)
        super.onDestroy()
    }


    private fun logEvent(duration: Long) {
        val userId = sharedPreferencesManager.getUserProfile().id
        val userName = sharedPreferencesManager.getUserProfile().name
        val firebaseAnalytics = FirebaseAnalytics.getInstance(baseContext)
        firebaseAnalytics.setUserId(userId)
        firebaseAnalytics.setUserProperty("user_name",userName)
        firebaseAnalytics.logEvent("daily_program_duration") {
            param("duration_ms", duration)
        }
    }
}
