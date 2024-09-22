package mnshat.dev.myproject.users.caregiver.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.ActivityCaregiverScreenBinding

class CaregiverScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaregiverScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCaregiverScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_caregiver_screen)
        navView.setupWithNavController(navController)
    }
}