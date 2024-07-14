package mnshat.dev.myproject.users.patient.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseActivity
import mnshat.dev.myproject.databinding.ActivityUserScreensBinding

class UserScreensActivity : BaseActivity() {

    private lateinit var binding: ActivityUserScreensBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserScreensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_user)
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.patientHomeFragment -> {
                    navController.popBackStack(R.id.patientHomeFragment, false)
                    navController.navigate(R.id.patientHomeFragment)
                    true
                }
                R.id.profileFragment -> {
                    navController.popBackStack(R.id.profileFragment, false)
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.messagesFragment -> {
                    navController.popBackStack(R.id.messagesFragment, false)
                    navController.navigate(R.id.messagesFragment)
                    true
                }
                else -> false
            }
        }
    }
}