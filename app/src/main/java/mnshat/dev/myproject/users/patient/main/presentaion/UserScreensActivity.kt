package mnshat.dev.myproject.users.patient.main.presentaion

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseActivity
import mnshat.dev.myproject.databinding.ActivityUserScreensBinding
import mnshat.dev.myproject.util.log
@AndroidEntryPoint
class UserScreensActivity : BaseActivity<ActivityUserScreensBinding>() {

    override fun getLayout(): ActivityUserScreensBinding {

       return ActivityUserScreensBinding.inflate(layoutInflater)

   }

    override fun initializeViews() {

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_user)
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener { item ->
            log(item.itemId!!.toString())

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
                R.id.chatting_navigation -> {
                    log("chatting_navigation")
                    navController.popBackStack(R.id.chatting_navigation, false)
                    navController.navigate(R.id.chatting_navigation)
                    true
                }
                R.id.calenderActivity -> {
                    log("calenderActivity")
                    navController.popBackStack(R.id.calenderActivity, false)
                    navController.navigate(R.id.calenderActivity)
                    true
                }
                else -> false
            }
        }
    }

}