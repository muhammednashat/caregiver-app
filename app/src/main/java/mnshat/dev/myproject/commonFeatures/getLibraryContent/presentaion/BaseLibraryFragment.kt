package mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.LibraryContentRepo
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.useCase.GetLibraryContentUseCase
import mnshat.dev.myproject.dataSource.room.AppDatabase
import mnshat.dev.myproject.factories.LibraryViewModelFactory
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import javax.inject.Inject

@AndroidEntryPoint

abstract class BaseLibraryFragment<T : ViewDataBinding> : BaseFragment<T>(),
    OnItemLibraryContentClicked {
    lateinit var viewModel: LibraryViewModel

@Inject lateinit var libraryContentRepo: LibraryContentRepo

    override fun initializeViews() {
        super.initializeViews()
        initViewModel()
    }

    fun displaySuggestedContent(
        onItemLibraryContentClicked: OnItemLibraryContentClicked,
        title: String,
        type: String
    ) {

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

//        val libraryDao = AppDatabase.getDatabase(requireContext()).libraryDao()
//        val libraryContentRepo = LibraryContentRepo(libraryDao)
//        libraryContentRepo
        val getLibraryContentUseCase = GetLibraryContentUseCase(libraryContentRepo)
        val factory = LibraryViewModelFactory(sharedPreferences, getLibraryContentUseCase,activity?.application!!)
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