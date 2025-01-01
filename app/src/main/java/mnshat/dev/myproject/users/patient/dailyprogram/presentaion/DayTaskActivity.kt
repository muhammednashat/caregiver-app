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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_task)
    }
}