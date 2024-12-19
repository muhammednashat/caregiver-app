package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.ActivityDayTaskBinding
@AndroidEntryPoint
class DayTaskActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_task)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_day_task) as NavHostFragment
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