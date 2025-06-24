package mnshat.dev.myproject.auth.data.repo

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.auth.data.entity.UserProfile
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.INVITATION_CODE
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.USERS
import mnshat.dev.myproject.util.USER_PROFILE
import mnshat.dev.myproject.util.log

class AuthRepo(
    private val firestore: FirebaseFirestore,
    private val fireAuth: FirebaseAuth,
    private val firebaseMessaging: FirebaseMessaging,
    private val sharedPreferences: SharedPreferencesManager
) {


    suspend fun signUp(userProfile: UserProfile):String {
      return   try {
            val (authResult , errorOrId) =
                createUserWithEmailAndPassword(userProfile.email!!, userProfile.password!!)
               if (authResult){
                val newUserProfile = newUserProfile(userProfile, errorOrId)
                storeUserDataRemote(newUserProfile)
                storeUserDataLocally(newUserProfile)
                   ""
               }else{
                   errorOrId
               }
        }catch (e:Exception){
            e.message.toString()
        }
    }

    private suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ):Pair<Boolean, String> {
        return try {
            val result = fireAuth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid
            if (uid != null) {
                Pair(true, uid)
            } else {
                Pair(false, "Registration failed: UID is null")
            }
        } catch (e: Exception) {
            Pair(false,e.message ?: "Registration error" )
        }
    }


    private suspend fun newUserProfile(
        userProfile: UserProfile,
        userId: String
    ): UserProfile {
        userProfile.imageUser =
            if (userProfile.gender == 1) "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2Fman.png?alt=media&token=442a95f6-c82c-4725-9432-5fef0b516b06" else "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2Fusers%20(2).png?alt=media&token=889b15ae-fd46-4255-a757-de712e1b5113"
        userProfile.password = null
        userProfile.id = userId
        userProfile.token = getToken()
        if (userProfile.typeOfUser == CAREGIVER) {
            userProfile.hasPartner = true
            userProfile.gender = null
            userProfile.religion = null
            userProfile.ageGroup = null
            userProfile.status = 1
            userProfile.numberSupporters = null
            userProfile.isInvitationUsed = null
        } else {
            val invitationCode = userId.take(8)
            userProfile.invitationCode = invitationCode
            userProfile.invitationBase = invitationCode
        }
        return userProfile

    }

    private suspend fun storeUserDataRemote(newUserProfile: UserProfile): Boolean {
        return try {
            firestore
                .collection(USERS)
                .document(newUserProfile.id!!)
                .set(newUserProfile)
                .await()
            true
        }catch (e:Exception){
            false
        }
    }

    private suspend fun getToken(): String {
        return try {
            firebaseMessaging.token.await()
        } catch (e: Exception) {
            ""
        }
    }

    private fun storeUserDataLocally(userProfile: UserProfile): Boolean {
        return try {
            sharedPreferences.storeObject(USER_PROFILE, userProfile)
//            val userProfile = sharedPreferences.getUserProfile(USER_PROFILE)
//            log(userProfile.toString())
            true
        } catch (e: Exception) {
            false
        }
    }


    private suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult? {
        return try {
            fireAuth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun isValidInvitation(invitationCode: String): UserProfile? {
        return try {
            val snapshot = firestore.collection(USERS)
                .whereEqualTo(INVITATION_CODE, invitationCode).get().await()
            return snapshot.documents.firstOrNull()?.toObject(UserProfile::class.java)
        } catch (e: Exception) {
            null
        }

    }

    fun retrievePartners() {

    }

    fun addPartner() {

    }

    fun retrieveUserRemoteByEmail() {

    }

    fun retrieveUserLocal() {

    }






    fun resetPassword() {

    }





}