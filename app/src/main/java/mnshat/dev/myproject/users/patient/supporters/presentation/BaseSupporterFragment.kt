package mnshat.dev.myproject.users.patient.supporters.presentation

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import mnshat.dev.myproject.users.patient.main.BasePatientFragment

open abstract class BaseSupporterFragment<T : ViewDataBinding> : BasePatientFragment<T>() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}