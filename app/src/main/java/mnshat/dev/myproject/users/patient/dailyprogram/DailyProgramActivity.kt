package mnshat.dev.myproject.users.patient.dailyprogram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
@AndroidEntryPoint
class DailyProgramActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_program)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_daily_program) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onBackPressed() {
        val currentDestination = navController.currentDestination
        when (currentDestination?.id) {
            R.id.congratulationsFragment -> {
                finish()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}