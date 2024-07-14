package mnshat.dev.myproject.base

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import mnshat.dev.myproject.util.MyApplication
import mnshat.dev.myproject.util.SharedPreferencesManager

 open class BaseActivity: AppCompatActivity() {

     lateinit var sharedPreferences: SharedPreferencesManager

     override fun onStart() {
         super.onStart()
         sharedPreferences = (application as MyApplication).sharedPreferences
     }
     fun getIDUserInFirebase(): String?{
         val currentUser = FirebaseAuth.getInstance().currentUser
         return currentUser?.uid
     }
}