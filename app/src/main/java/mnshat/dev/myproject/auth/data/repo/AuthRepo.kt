package mnshat.dev.myproject.auth.data.repo

import mnshat.dev.myproject.auth.data.entity.RegistrationData
import mnshat.dev.myproject.util.SharedPreferencesManager

class AuthRepo (
    private val authApi: AuthApi,
    private val sharedPreferences: SharedPreferencesManager
){





    fun signUp(registrationData: RegistrationData){

    }

    fun login(){

    }

    fun forgotPassword(){

    }

    fun resetPassword(){

    }

    fun verifyCode(){

    }

    fun resendCode(){

    }

    fun storeDataLocally(){

    }

    fun clearData(){

    }

    fun storeToken(){

    }

    fun storeDataRemote(){

    }
}