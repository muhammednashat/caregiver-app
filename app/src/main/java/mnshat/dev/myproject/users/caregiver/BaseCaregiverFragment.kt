package mnshat.dev.myproject.users.caregiver

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.base.BaseFragment2

open class BaseCaregiverFragment: BaseFragment2() {
    lateinit var _viewModel: CaregiverViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _viewModel = ViewModelProvider(this)[CaregiverViewModel::class.java]
    }
}