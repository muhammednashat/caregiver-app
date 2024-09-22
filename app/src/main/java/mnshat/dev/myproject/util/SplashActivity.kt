package mnshat.dev.myproject.util

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Handler
import android.util.DisplayMetrics
import android.view.WindowManager
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.AuthActivity
import mnshat.dev.myproject.base.BaseActivity
import mnshat.dev.myproject.databinding.ActivitySplashBinding
import mnshat.dev.myproject.users.caregiver.main.CaregiverScreenActivity
import mnshat.dev.myproject.users.patient.main.UserScreensActivity
import java.util.Locale


class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getLayout(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initializeViews() {
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
