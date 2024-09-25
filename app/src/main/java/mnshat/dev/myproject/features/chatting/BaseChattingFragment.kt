package mnshat.dev.myproject.features.chatting

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.factories.ChattingViewModelFactory
import mnshat.dev.myproject.factories.LibraryViewModelFactory
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.VIDEO
import mnshat.dev.myproject.util.log

abstract class BaseChattingFragment<T: ViewDataBinding> : BaseFragment<T>()
     {

     lateinit var viewModel: ChattingViewModel


    override fun initializeViews() {
        super.initializeViews()
        initViewModel()
    }



    private fun initViewModel() {
        val factory = ChattingViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[ChattingViewModel::class.java]

    }
}