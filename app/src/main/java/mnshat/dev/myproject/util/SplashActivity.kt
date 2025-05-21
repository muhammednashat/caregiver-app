package mnshat.dev.myproject.util

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.presentation.AuthActivity
import mnshat.dev.myproject.base.BaseActivity
import mnshat.dev.myproject.databinding.ActivitySplashBinding
import mnshat.dev.myproject.users.caregiver.main.CaregiverScreenActivity
import mnshat.dev.myproject.users.patient.main.presentaion.UserScreensActivity
import java.util.Locale



class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getLayout(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initializeViews() {


//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = Color.TRANSPARENT
//        val background: Drawable =
//            this@SplashActivity.getResources().getDrawable(R.drawable.background53)
//        window.setBackgroundDrawable(background)

        Handler().postDelayed({

//            isLogged()
            setLocale(if (sharedPreferences.getString(LANGUAGE) == "en") "en" else "ar")
            styleText(if (sharedPreferences.getString(LANGUAGE) == "en") "en" else "ar")
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



    private fun styleText(lang:String) {
//        val textView = findViewById<TextView>(R.id.text1)
//        val text = getString(R.string.note_before_you_begin)
//        val spannable = SpannableString(text)
//        val start = if(lang == "ar") text.indexOf("ملاحظة") else text.indexOf("Note")
//        val end =  if(lang == "ar") text.indexOf("قبل") else  text.indexOf("before")
//        val vectorDrawable = ContextCompat.getDrawable(this, R.drawable.zigzag_line)
//        vectorDrawable?.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
//
//        val imageSpan = ImageSpan(vectorDrawable!!, ImageSpan.ALIGN_BASELINE)
//         spannable.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
////        spannable.setSpan(ForegroundColorSpan(Color.parseColor("#204167")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
////        spannable.setSpan(BackgroundColorSpan(Color.parseColor("#c2e3f8")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
////        spannable.setSpan(AbsoluteSizeSpan(16, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
////        spannable.setSpan(StyleSpan(android.graphics.Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        textView.text = spannable
    }
}
