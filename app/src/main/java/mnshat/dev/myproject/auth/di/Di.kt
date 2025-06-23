package mnshat.dev.myproject.auth.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mnshat.dev.myproject.auth.data.repo.AuthRepo
import mnshat.dev.myproject.auth.presentation.AuthViewModel
import mnshat.dev.myproject.util.SharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Di {

    @Provides
    @Singleton
    fun provideAuthViewModel(
        sharedPreferences: SharedPreferencesManager,
        authRepo: AuthRepo
    ) = AuthViewModel(sharedPreferences, authRepo)


    @Provides
    @Singleton
    fun provideAuthRepo(
        firestore: FirebaseFirestore,
        fireAuth: FirebaseAuth,
        firebaseMessaging: FirebaseMessaging,
        sharedPreferences: SharedPreferencesManager
    ) = AuthRepo(firestore, fireAuth, firebaseMessaging, sharedPreferences)


}