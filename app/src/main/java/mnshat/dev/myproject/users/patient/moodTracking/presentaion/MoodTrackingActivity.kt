package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
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