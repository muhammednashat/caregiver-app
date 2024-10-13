package mnshat.dev.myproject.commonFeatures.libraraycontent

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.factories.LibraryViewModelFactory
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked

abstract class BaseLibraryFragment<T: ViewDataBinding> : BaseFragment<T>() ,
    OnItemLibraryContentClicked {
     lateinit var viewModel: LibraryViewModel


    override fun initializeViews() {
        super.initializeViews()
        initViewModel()
    }

    fun displaySuggestedContent(onItemLibraryContentClicked: OnItemLibraryContentClicked,title: String, type: String) {

        val suggestedContentFragment =
            SuggestedContentFragment()
                .setType(type)
                .setOnItemLibraryContent(onItemLibraryContentClicked)
                .setTitle(title)
        suggestedContentFragment.show(
            childFragmentManager,
            suggestedContentFragment::class.java.name
        )
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