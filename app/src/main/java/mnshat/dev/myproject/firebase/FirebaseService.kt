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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import mnshat.dev.myproject.R
import mnshat.dev.myproject.model.CurrentTask
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.EMAIL
import mnshat.dev.myproject.util.INVITATION_CODE

object FirebaseService {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    val usersDatabase = firebaseDatabase.getReference("users")
    val tasksUsersDatabase = firebaseDatabase.getReference("tasks_users")
    val supplicationsUsersDatabase = firebaseDatabase.getReference("supplications_users")

    private val fireAuth = FirebaseAuth.getInstance()
    private val currentUser = fireAuth.currentUser
    val userEmail = currentUser?.email

    val userId = currentUser?.uid
    fun logOut() = fireAuth.signOut()

    fun login(email: String, password: String, callBack: (String?) -> Unit) {

        fireAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callBack(null)
                } else {
                    callBack(task.exception.toString())
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
            usersDatabase.orderByChild(INVITATION_CODE).equalTo(string)
        else
            usersDatabase.orderByChild(EMAIL).equalTo(string)
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

    fun retrieveUsersByEmails(emails: List<String>?, callback: (List<RegistrationData>?) -> Unit) {
        emails?.let {
            val query = usersDatabase.orderByChild(EMAIL).apply {
                emails?.forEach { email ->
                    equalTo(email)
                }
            }

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userList = mutableListOf<RegistrationData>()

                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(RegistrationData::class.java)
                        user?.let {
                            if (it.email != userEmail) {
                                userList.add(it)
                            }
                        }
                    }
                    callback(userList.takeIf { it.isNotEmpty() })
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback(null)
                }
            })
        }
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
        usersDatabase.child(registrationData.id!!).setValue(registrationData)
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
            usersDatabase.child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        usersDatabase.child(userId).updateChildren(map)
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
            tasksUsersDatabase.child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        tasksUsersDatabase.child(userId).updateChildren(map)
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

        usersDatabase.child(userId!!).addValueEventListener(userListener)

    }

    fun retrieveCurrentTasksForUser(userEmail: String, callBack: (CurrentTask?) -> Unit) {

        val query = tasksUsersDatabase.orderByChild(EMAIL).equalTo(userEmail)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val currentTask = snapshot.getValue(CurrentTask::class.java)
                        if (currentTask != null) {
                            Log.e("TAG", "currentTask 1 ${currentTask.email}")
                            callBack(currentTask)
                            return
                        }
                    }
                    Log.e("TAG", "No matching task found")
                    callBack(null)
                } else {
                    Log.e("TAG", "DataSnapshot does not exist")
                    callBack(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "Database error: ${error.message}")
                callBack(null)
            }
        })
    }

    fun storeCurrentTaskRemotely(
        userId: String,
        currentTask: CurrentTask,
        callBack: (CurrentTask?) -> Unit
    ) {


        if (userId != null) {
            tasksUsersDatabase.child(userId!!).setValue(
                currentTask
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callBack(currentTask)
                    } else {
                        callBack(null)
                    }
                }
        } else {
        }

    }

}
