package mnshat.dev.myproject.auth.data.repo

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import mnshat.dev.myproject.auth.data.entity.UserProfile
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

    suspend fun getToken(): String {
        return try {
            firebaseMessaging.token.await()
        } catch (e: Exception) {
            ""
        }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult? {
        return try {
            fireAuth.signInWithEmailAndPassword(email, password).await()

        } catch (e: Exception) {
            null
        }
    }

    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthResult? {
        return try {
            fireAuth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            null
        }
    }

    fun storeUserDataLocally( userProfile: UserProfile): Boolean {
        return try {
            sharedPreferences.storeObject(USER_PROFILE, userProfile)
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun storeUserDataRemote( userProfile:  UserProfile): Boolean {
        return try {
            firestore
                .collection(USERS)
                .document( userProfile.id!!)
                .set( userProfile)
                .await()
            log("storeUserDataRemote()")
            true
        }catch (e:Exception){
            log("${e.toString()}")
            false
        }
    }


    fun isValidInvitation(invitationCode: String) {

    }

    fun retrievePartners() {

    }

    fun addPartner() {

    }

    fun retrieveUserRemoteByEmail() {

    }

    fun retrieveUserLocal() {

    }


    fun signUp( userProfile:  UserProfile) {

    }




    fun resetPassword() {

    }

    fun verifyCode() {

    }

    fun resendCode() {

    }



    fun storeTokenRemote() {

    }


}