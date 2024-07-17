package mnshat.dev.myproject.users.patient.supporters

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.users.patient.main.BasePatientFragment

open abstract class BaseSupporterFragment<T : ViewDataBinding> : BasePatientFragment<T>() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}