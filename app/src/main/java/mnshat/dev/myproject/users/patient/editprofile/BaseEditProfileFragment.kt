package mnshat.dev.myproject.users.patient.editprofile

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.users.patient.main.BasePatientFragment

open abstract class BaseEditProfileFragment<T : ViewDataBinding> : BasePatientFragment<T>() {

     lateinit var _viewModel: EditProfileViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        _viewModel = ViewModelProvider(requireActivity())[EditProfileViewModel::class.java]
        super.onActivityCreated(savedInstanceState)
    }


}