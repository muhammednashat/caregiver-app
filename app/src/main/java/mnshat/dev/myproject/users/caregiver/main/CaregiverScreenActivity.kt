package mnshat.dev.myproject.users.caregiver.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseActivity
import mnshat.dev.myproject.databinding.ActivityCaregiverScreenBinding
import mnshat.dev.myproject.databinding.ActivityUserScreensBinding
import mnshat.dev.myproject.util.log

class CaregiverScreenActivity : BaseActivity<ActivityCaregiverScreenBinding>() {

    override fun getLayout(): ActivityCaregiverScreenBinding {

        return ActivityCaregiverScreenBinding.inflate(layoutInflater)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCaregiverScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun initializeViews() {

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_caregiver_screen)
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.caregiverHomeFragment -> {
                    log("caregiverHomeFragment")

                    navController.popBackStack(R.id.caregiverHomeFragment, false)
                    navController.navigate(R.id.caregiverHomeFragment)
                    true
                }
                R.id.caregiverProfileFragment -> {
                    log("caregiverProfileFragment")

                    navController.popBackStack(R.id.caregiverProfileFragment, false)
                    navController.navigate(R.id.caregiverProfileFragment)
                    true
                }
                R.id.chatting_navigation -> {
                    log("chatting_navigation")
                    navController.popBackStack(R.id.chatting_navigation, false)
                    navController.navigate(R.id.chatting_navigation)
                    true
                }
                else -> false
            }
        }
    }



}