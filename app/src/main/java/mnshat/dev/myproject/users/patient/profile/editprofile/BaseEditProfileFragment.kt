package mnshat.dev.myproject.users.patient.profile.editprofile

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.users.patient.main.BasePatientFragment

open abstract class BaseEditProfileFragment<T : ViewDataBinding> : BasePatientFragment<T>() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}