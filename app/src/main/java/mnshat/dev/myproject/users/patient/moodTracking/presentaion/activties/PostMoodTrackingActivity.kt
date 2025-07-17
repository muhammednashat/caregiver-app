package mnshat.dev.myproject.users.patient.moodTracking.presentaion.activties

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
@AndroidEntryPoint
class PostMoodTrackingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_mood_tracking)
    }
}