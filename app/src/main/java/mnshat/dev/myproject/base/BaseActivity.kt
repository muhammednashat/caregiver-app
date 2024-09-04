package mnshat.dev.myproject.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.FirebaseAuth
import mnshat.dev.myproject.databinding.ActivityUserScreensBinding
import mnshat.dev.myproject.util.MyApplication
import mnshat.dev.myproject.util.SharedPreferencesManager

 abstract class BaseActivity<T: ViewBinding>: AppCompatActivity() {

     lateinit var sharedPreferences: SharedPreferencesManager

     lateinit var binding: T
     abstract fun getLayout(): T
     open fun initializeViews(){}

     override fun onStart() {
         super.onStart()
         sharedPreferences = (application as MyApplication).sharedPreferences
     }

     fun getIDUserInFirebase(): String?{
         val currentUser = FirebaseAuth.getInstance().currentUser
         return currentUser?.uid
     }
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = getLayout()
         setContentView(binding.root)
         initializeViews()

     }
     }
