package mnshat.dev.myproject.util

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.WindowManager
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.AuthActivity
import mnshat.dev.myproject.base.BaseActivity2
import mnshat.dev.myproject.users.admin.AdminScreenActivity
import mnshat.dev.myproject.users.caregiver.CaregiverScreenActivity
import mnshat.dev.myproject.users.patient.main.UserScreensActivity
import java.util.Locale


class SplashActivity : BaseActivity2() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        val background: Drawable =
            this@SplashActivity.getResources().getDrawable(R.drawable.img_background_auth)
        window.setBackgroundDrawable(background)
        Handler().postDelayed({
            isLogged()
        setLocale(if (sharedPreferences.getString(LANGUAGE) == "en") "en" else "ar")
        }, 2000)

    }

    private fun isLogged() {
        if (sharedPreferences.getBoolean(IS_LOGGED)) {
            getTypeUser()
        } else {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }
    private fun getTypeUser() {
          when(sharedPreferences.getString(TYPE_OF_USER)){
              USER -> {
                  startActivity(Intent(this, UserScreensActivity::class.java))
              }
              CAREGIVER -> {
                  startActivity(Intent(this, CaregiverScreenActivity::class.java))
              }
              else -> {
                  startActivity(Intent(this, AdminScreenActivity::class.java))
              }
          }
            finish()
    }

    fun setLocale(lang: String?) {
        val resources: Resources = resources
        val dm: DisplayMetrics = resources.displayMetrics
        val conf: Configuration = resources.configuration
        conf.setLocale(Locale(lang))
        if (lang == "ar") {
            conf.setLayoutDirection(Locale(lang))
        } else {
            conf.setLayoutDirection(Locale.ENGLISH)
        }
        resources.updateConfiguration(conf, dm)
    }
}
