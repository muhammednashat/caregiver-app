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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mnshat.dev.myproject.R
import mnshat.dev.myproject.R.string.have_you_had_the_opportunity_to_help_someone_new_how_do_you_feel_about_that
import mnshat.dev.myproject.model.DayTask
import mnshat.dev.myproject.model.Task

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

fun addDailyTasks(){
    val db = Firebase.firestore
    val dayTask = DayTask()

    val task1 = Task()
    val task2 = Task()
    val task3 = Task()
    val task4 = Task()

    task1.type = 1
    task1.arTitle = "تحدى اليوم"
    task1.enTitle = "challenge today"
    task1.arDescription =
        "فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام"
    task1.enDescription =
        "In this task you have to do In this task you have to do In this task you have to do In this task you have to do"
    task1.image =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2F71GNZ%2Bf6zyL._AC_SL1500_.jpg?alt=media&token=bfeb8c1b-0e47-420a-8cb7-3161aab2c1d6"
    task1.gender = 1
    task1.ageGroup = 1
    task1.religion = true

    task2.type = 2
    task2.arTitle = "مشاهدة الفديو"
    task2.enTitle = "See Video"
    task2.arDescription =
        "فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام"
    task2.enDescription =
        "In this task you have to do In this task you have to do In this task you have to do In this task you have to do"
    task2.link =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/videos%2F3%20_Binary%20Search%20-%20Recursive%20implementation(720P_HD).mp4?alt=media&token=78e30d71-1b51-4ff6-9d0b-de3f6365aff1"
    task2.gender = 1
    task2.ageGroup = 1
    task2.religion = true


    task3.type = 3
    task3.arTitle = "اﻹستماع الى البودكاست"
    task3.enTitle = "Listening to podcast"
    task3.arDescription =
        "فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام"
    task3.enDescription =
        "In this task you have to do In this task you have to do In this task you have to do In this task you have to do"
    task3.link =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/audios%2F%D8%B9%D9%84%D9%89%20%D9%82%D8%AF%D8%B1%20%D8%A7%D9%87%D9%84%20%D8%A7%D9%84%D8%B9%D8%B2%D9%85.mp3?alt=media&token=bf6e8bbc-a897-4f04-ae55-5b4922aaa1c2"
    task3.image =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2F71GNZ%2Bf6zyL._AC_SL1500_.jpg?alt=media&token=bfeb8c1b-0e47-420a-8cb7-3161aab2c1d6"
    task3.gender = 1
    task3.ageGroup = 1
    task3.religion = true

    task4.type = 1
    task4.arTitle = "تحدى اليوم"
    task4.enTitle = "challenge today"
    task4.arDescription =
        "فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام"
    task4.enDescription =
        "In this task you have to do In this task you have to do In this task you have to do In this task you have to do"
    task4.link =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/audios%2F%D8%B9%D9%84%D9%89%20%D9%82%D8%AF%D8%B1%20%D8%A7%D9%87%D9%84%20%D8%A7%D9%84%D8%B9%D8%B2%D9%85.mp3?alt=media&token=bf6e8bbc-a897-4f04-ae55-5b4922aaa1c2"
    task4.image =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2Fimg1.jpg?alt=media&token=d0823e7a-416f-4798-ac0a-ee91976c8082"
    task4.gender = 1
    task4.ageGroup = 1
    task4.religion = true



    dayTask.educational = listOf(task1, task2, task3, task4)
    dayTask.behaviorActivation = listOf(task1, task1, task1, task1,task1)
    dayTask.spiritual = listOf(task1, task2, task3, task4)


    for (x in 1..30) {
        db.collection("daily_programs").document("" + x).set(dayTask)
    }



}
