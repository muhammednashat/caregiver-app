package mnshat.dev.myproject.users.patient.libraraycontent

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.factories.LibraryViewModelFactory
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.VIDEO
import mnshat.dev.myproject.util.log

abstract class BaseLibraryFragment<T: ViewDataBinding> : BasePatientFragment<T>() ,
    OnItemLibraryContentClicked {
     lateinit var viewModel: LibraryViewModel


    override fun initializeViews() {
        super.initializeViews()
        initViewModel()
    }



    private fun initViewModel() {
        val factory = LibraryViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[LibraryViewModel::class.java]
    }

    override fun onItemClicked(type: String, index: Int, content: String) {
        updateCurrentContent(content)
        updateCurrentIndex(index)

    }

    fun updateCurrentIndex(index: Int) {
        viewModel.setCurrentContentIndex(index)
    }

    fun updateCurrentContent(content: String) {
        viewModel.setCurrentContentContent(content)
    }
}