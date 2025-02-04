package mnshat.dev.myproject.firebase

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.R
import mnshat.dev.myproject.model.CurrentTask2
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.dataSource.room.AppDatabase
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.DayTaskEntity
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.CURRENT_DAY
import mnshat.dev.myproject.util.DAILY_PROGRAM_STATES
import mnshat.dev.myproject.util.USER_EMAIL
import mnshat.dev.myproject.util.INVITATION_CODE
import mnshat.dev.myproject.util.LIBRARY_CONTENTS
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USER_PROFILES
import mnshat.dev.myproject.util.log
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object FirebaseService {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    val userProfiles = firebaseDatabase.getReference(USER_PROFILES)
    val dailyProgramStates = firebaseDatabase.getReference(DAILY_PROGRAM_STATES)
     val libraryContents = firebaseDatabase.getReference(LIBRARY_CONTENTS)

    private val fireAuth = FirebaseAuth.getInstance()
    private val currentUser = fireAuth.currentUser
    val userEmail = currentUser?.email

    val userId = currentUser?.uid
    fun logOut() = fireAuth.signOut()

  suspend  fun login(email: String, password: String,context: Context,sharedPreferences: SharedPreferencesManager, callBack: (String?) -> Unit) {

        fireAuth.signInWithEmailAndPassword(email, password).await().let { auth ->
            if (auth.user != null) {
                fetchDays().let {
                    //
                    AppDatabase.getDatabase(context).dayTaskDao().insertAll(it)
                    getCurrentDayRemotely(auth?.user?.uid!!){
                        log(it?.email.toString())
                        sharedPreferences.storeObject(CURRENT_DAY, it!!)
                        callBack(null)
                    }
                }

            } else {
                callBack("")
            }
        }
    }


    fun signUp(email: String, password: String, callBack: (Boolean, String?) -> Unit) {
        fireAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callBack(true, task.result?.user?.uid)
                } else {
                    callBack(false, task.exception.toString())
                }
            }
    }



    fun getToken(callback: (String?) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(task.result)
            } else {
                callback(null)
            }
        }
    }

    fun retrieveUser(typeOfUser: String?, string: String?, callback: (RegistrationData?) -> Unit) {
        val query = if (typeOfUser == CAREGIVER)
            userProfiles.orderByChild(INVITATION_CODE).equalTo(string)
        else
            userProfiles.orderByChild(USER_EMAIL).equalTo(string)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(RegistrationData::class.java)
                        callback(user)
                        return
                    }
                } else {
                    callback(null)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(null)
            }
        })
    }



    fun changeCurrentPassword(
        oldPassword: String,
        newPassword: String,
        context: Context,
        callBack: (String?) -> Unit
    ) {
        val credential = EmailAuthProvider.getCredential(userEmail!!, oldPassword)
        currentUser?.reauthenticate(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                currentUser.updatePassword(newPassword).addOnCompleteListener { task ->
                    if (task.isSuccessful) callBack(null)
                    else callBack(task.exception.toString())
                }
            } else callBack(context.getString(R.string.check_entered_password))

        }
    }

    fun resetUserPassword(email: String, callBack: (Boolean) -> Unit) {
        fireAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                handleResult(task, callBack)
            }
    }

    private fun handleResult(
        task: Task<Void>,
        callBack: (Boolean) -> Unit
    ) {
        if (task.isSuccessful) {
            callBack(true)
        } else {
            callBack(false)
        }
    }

    fun saveUserAdditionalInfo(registrationData: RegistrationData) {
        userProfiles.child(registrationData.id!!).setValue(registrationData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                }

            }
    }

    fun updateItemsProfileUser(
        userId: String? ,
        map: Map<String, Any?>,
        callBack: (Boolean) -> Unit
    ) {
        if (userId != null) {
            userProfiles.child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        userProfiles.child(userId).updateChildren(map)
                            .addOnCompleteListener { task ->
                                callBack(task.isSuccessful)
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    fun updateTasksUser(userId: String?, map: Map<String, Any?>, callBack: (Boolean) -> Unit) {
        if (userId != null) {
            dailyProgramStates.child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        dailyProgramStates.child(userId).updateChildren(map)
                            .addOnCompleteListener { task ->
                                callBack(task.isSuccessful)
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callBack(false)
                }
            })
        }
    }


    fun retrieveUser(userId:String,callBack: (RegistrationData?) -> Unit) {
        log(userId)
        userProfiles.child(userId).get().addOnSuccessListener { dataSnapShot->
            val user = dataSnapShot.getValue(RegistrationData::class.java)
            if (user != null) {
                log(user.email!!)
                callBack(user)
            } else {
                callBack(null)
            }
        }
    }

    fun retrieveUsers(emails: List<String>?, callback: (List<RegistrationData>?) -> Unit){
        userProfiles.get().addOnSuccessListener { snapshot ->
            val matchingProfiles = snapshot.children.filter { dataSnapshot ->
                val email = dataSnapshot.child(USER_EMAIL).getValue(String::class.java)
                log("email - > $email")
                emails?.contains(email) == true
            }
            val userList = mutableListOf<RegistrationData>()

            matchingProfiles.forEach { profile ->
                userList.add(profile.getValue(RegistrationData::class.java)!!)
                println("Matching user: ${profile.value}")
            }
            callback(userList)

        }
            .addOnFailureListener { e ->
                println("Error fetching data: ${e.message}")
            }
    }
    fun listenForUserDataChanges(callBack: (RegistrationData?) -> Unit) {
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(RegistrationData::class.java)
                if (user != null) {
                    callBack(user)
                } else {
                    callBack(null)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                callBack(null)
                Log.e("TAG", "loadUserData:onCancelled ${databaseError.toException()}")
            }
        }

        userProfiles.child(userId!!).addValueEventListener(userListener)

    }

     private fun getCurrentDayRemotely(userId: String, callBack: (CurrentDay?) -> Unit) {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val dailyProgramStates = firebaseDatabase.getReference(DAILY_PROGRAM_STATES)
         dailyProgramStates.child(userId).get().addOnSuccessListener {
             val value = it.getValue(CurrentDay::class.java)
            callBack(value)
          }
    }






    fun getLibraryContent(onDataFetched: (List<LibraryContent>?) -> Unit) {

        libraryContents.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val libraryContentList = mutableListOf<LibraryContent>()
                for (contentSnapshot in snapshot.children) {
                    val libraryContent = contentSnapshot.getValue(LibraryContent::class.java)
                    libraryContent?.let {
                        libraryContentList.add(it)
                    }
                }
                onDataFetched(libraryContentList)
            }

            override fun onCancelled(error: DatabaseError) {
                onDataFetched(null)
                println("Failed to retrieve data: ${error.message}")
            }
        })

    }

    private suspend fun fetchDays():
            List<DayTaskEntity> = suspendCoroutine { continuation ->
        val dayTaskList = mutableListOf<DayTaskEntity>()
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("daily_programs")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val dayTask = document.toObject(DayTaskEntity::class.java)
                    dayTaskList.add(dayTask)
                }
                continuation.resume(dayTaskList)
            }
            .addOnFailureListener { e ->
                continuation.resume(emptyList())
                println("Error fetching data: $e")
            }
    }


}
