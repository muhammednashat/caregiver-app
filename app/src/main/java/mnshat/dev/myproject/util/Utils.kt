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

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}


fun showSnackbarForInternetStatus(context: Context, view: View) {
    view?.let { Snackbar.make(it, "No internet connection", Snackbar.LENGTH_LONG).show() }
}



fun log(text:String,tag:String = "TAG" ) = Log.e(tag , text)


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

fun getListHands()= listOf(
    R.drawable.image_hand1,
    R.drawable.image_hand2,
    R.drawable.image_hand3,
    R.drawable.image_hand4,
    R.drawable.image_hand5,
    R.drawable.image_hand6,
    R.drawable.image_hand7,
    R.drawable.image_hand8,
    R.drawable.image_hand9,
    R.drawable.image_hand10,
    R.drawable.image_hand11,
    R.drawable.image_hand12,
    R.drawable.image_hand13,
    R.drawable.image_hand14,
    R.drawable.image_hand15,
    R.drawable.image_hand16,
)
fun getListSebha()= listOf(
    R.drawable.image_sebha0,
    R.drawable.image_sebha1,
    R.drawable.image_sebha2,
    R.drawable.image_sebha3,
    R.drawable.image_sebha4,
    R.drawable.image_sebha5,
    R.drawable.image_sebha6,
    R.drawable.image_sebha7,
    R.drawable.image_sebha8,
    R.drawable.image_sebha9,
    R.drawable.image_sebha10,
    R.drawable.image_sebha11,
    R.drawable.image_sebha12,
    R.drawable.image_sebha13,
    R.drawable.image_sebha14,
    R.drawable.image_sebha16,
    R.drawable.image_sebha17,
    R.drawable.image_sebha18,
    R.drawable.image_sebha19,
    R.drawable.image_sebha20,
    R.drawable.image_sebha21,
    R.drawable.image_sebha22,
)






