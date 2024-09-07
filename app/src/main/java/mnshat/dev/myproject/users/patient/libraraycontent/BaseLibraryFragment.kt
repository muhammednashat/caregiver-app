package mnshat.dev.myproject.users.patient.libraraycontent

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import mnshat.dev.myproject.R
import mnshat.dev.myproject.factories.LibraryViewModelFactory
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.AUDIO
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
    fun navigateToCustomizedContent() {
        val action = LibraryContentFragmentDirections
            .actionLibraryContentFragmentToCustomizedContentFragment(viewModel.mLibraryContentCustomized.toTypedArray())
        findNavController().navigate(action)
    }

    override fun onItemClicked(type: String, index: Int, content: String) {
        updateCurrentIndex(index)
        updateCurrentContent(content)
        log("$type    $index")
        when (type) {
            ARTICLE -> findNavController().navigate(R.id.action_libraryContentFragment_to_articleFragment)
            VIDEO -> findNavController().navigate(R.id.action_libraryContentFragment_to_videoFragment)
            else -> {""}
//            AUDIO -> AudioBottomSheetFragment().show( childFragmentManager, AudioBottomSheetFragment::class.java.name
//            )

        }
    }

    private fun updateCurrentIndex(index: Int) {
        viewModel.setCurrentContentIndex(index)
    }

    private fun updateCurrentContent(content: String) {
        viewModel.setCurrentContentContent(content)
    }
}