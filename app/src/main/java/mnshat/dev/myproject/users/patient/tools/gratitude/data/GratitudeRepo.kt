package mnshat.dev.myproject.users.patient.tools.gratitude.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.R
import mnshat.dev.myproject.R.string.have_you_had_the_opportunity_to_help_someone_new_how_do_you_feel_about_that
import mnshat.dev.myproject.users.patient.tools.gratitude.entity.Gratitude
import mnshat.dev.myproject.util.GRATITUDE
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USERS

class GratitudeRepo (
    private val sharedPreferences: SharedPreferencesManager,
    private val fireStore: FirebaseFirestore
) {

    val user = sharedPreferences.getUserProfile()
     val gratitudeList = MutableLiveData<List<Gratitude>>()

    fun getGratitudeQuestionsList(context: Context): List<String> {
        return listOf(
            context.getString(have_you_had_the_opportunity_to_help_someone_new_how_do_you_feel_about_that),
            context.getString(R.string.what_is_the_good_thing_that_happened_to_you_this_week),
            context.getString(R.string.what_is_the_nice_thing_someone_did_for_you_recently),
            context.getString(R.string.who_is_the_person_who_is_always_with_you_and_how_do_you_feel_about_them),
        )
    }

  suspend fun saveGratitudeRemotely(gratitude: Gratitude) {
        fireStore.collection(USERS).document(user.id!!).collection(GRATITUDE).add(gratitude).await()
    }


     fun retrieveGratitudeListRemotely(){
       fireStore.collection(USERS)
            .document(user.id!!)
            .collection(GRATITUDE)
            .addSnapshotListener { value, error ->
            val data =    value?.toObjects(Gratitude::class.java) ?: emptyList()

                gratitudeList.value  = data
            }

    }
}