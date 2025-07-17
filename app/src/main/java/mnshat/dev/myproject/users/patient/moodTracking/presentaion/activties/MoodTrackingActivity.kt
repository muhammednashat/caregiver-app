package mnshat.dev.myproject.users.patient.moodTracking.presentaion.activties

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.databinding.ActivityMoodTrackingBinding
@AndroidEntryPoint

class MoodTrackingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoodTrackingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoodTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}