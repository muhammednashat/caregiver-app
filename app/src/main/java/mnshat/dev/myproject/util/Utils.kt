package mnshat.dev.myproject.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import mnshat.dev.myproject.R
import mnshat.dev.myproject.R.string.have_you_had_the_opportunity_to_help_someone_new_how_do_you_feel_about_that
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}


fun errorSnackBar( view: View,text: String) {
    view?.let { Snackbar.make(it, text, Snackbar.LENGTH_LONG).show() }
}



fun log(text:String,tag:String = "TAG" ) {
    Log.e(tag , "=================================================")
    Log.e(tag , text)
    Log.e(tag , "=================================================")


}


fun getDateAsString(currentTimeMillis:Long):String{
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(currentTimeMillis))
}

fun dateTime(currentTimeMillis:Long):String{
    val sdf = SimpleDateFormat("dd/MM/yyyy  hh:mm a", Locale.getDefault())
    return sdf.format(Date(currentTimeMillis))
}


fun isUser(preferences: SharedPreferencesManager)=preferences.getString(TYPE_OF_USER)== USER

fun isValidInput(input:String) = input.isNotBlank()
fun setLocale( lang:String? , context: Context){
    var  res: android.content.res.Resources? = context.resources
    var  dm: DisplayMetrics? = res?.displayMetrics
    var  conf:android.content.res.Configuration? = res?.configuration
    conf?.setLocale(java.util.Locale(lang))
    if ((lang == "ar")){
        conf?.setLayoutDirection(java.util.Locale(lang))
    } else {
        conf?.setLayoutDirection(java.util.Locale.ENGLISH)
    }
    res?.updateConfiguration(conf, dm)
}

fun loadImage(context:Context,imageURL: String?,imageView: ImageView) {

    Glide.with(context).load(imageURL).into(imageView)
}
fun getGratitudeQuestionsList(context: Context): List<String> {
    return listOf(
        context.getString(have_you_had_the_opportunity_to_help_someone_new_how_do_you_feel_about_that),
        context.getString(R.string.what_is_the_good_thing_that_happened_to_you_this_week),
        context.getString(R.string.what_is_the_nice_thing_someone_did_for_you_recently),
        context.getString(R.string.who_is_the_person_who_is_always_with_you_and_how_do_you_feel_about_them),
    )
}







