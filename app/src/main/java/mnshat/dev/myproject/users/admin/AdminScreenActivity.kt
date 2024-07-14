package mnshat.dev.myproject.users.admin

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.ActivityAdminScreenBinding

class AdminScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_admin_screen)
        navView.setupWithNavController(navController)

    }
}