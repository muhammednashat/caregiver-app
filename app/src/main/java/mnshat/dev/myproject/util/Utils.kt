package mnshat.dev.myproject.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import mnshat.dev.myproject.R

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}


fun showSnackbarForInternetStatus(context: Context, view: View) {
    view?.let { Snackbar.make(it, "No internet connection", Snackbar.LENGTH_LONG).show() }
}

fun copyToClipboard(context: Context,textToCopy: String?) {
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("text", textToCopy)
    clipboardManager.setPrimaryClip(clipData)
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
)
