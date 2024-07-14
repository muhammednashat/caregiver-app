package mnshat.dev.myproject.users.patient.supporters

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.users.patient.main.BaseUserFragment

open abstract class BaseSupporterFragment<T : ViewDataBinding> : BaseUserFragment<T>() {

     lateinit var _viewModel: SupporterViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        _viewModel = ViewModelProvider(requireActivity())[SupporterViewModel::class.java]
        super.onActivityCreated(savedInstanceState)
    }


}