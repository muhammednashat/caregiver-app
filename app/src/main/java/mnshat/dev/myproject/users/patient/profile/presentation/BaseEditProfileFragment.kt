package mnshat.dev.myproject.users.patient.profile.presentation

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import mnshat.dev.myproject.users.patient.main.BasePatientFragment

open abstract class BaseEditProfileFragment<T : ViewDataBinding> : BasePatientFragment<T>() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}