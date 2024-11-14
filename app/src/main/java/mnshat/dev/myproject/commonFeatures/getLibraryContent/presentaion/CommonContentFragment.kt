package mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.CommonContentLibraryAdapter
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.LibraryContentRepo
import mnshat.dev.myproject.databinding.FragmentCommonContentBinding
import mnshat.dev.myproject.factories.LibraryViewModelFactory
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.useCase.GetLibraryContentUseCase
import mnshat.dev.myproject.dataSource.room.AppDatabase
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.VIDEO


class CommonContentFragment : BaseBottomSheetDialogFragment<FragmentCommonContentBinding>(),
    OnItemLibraryContentClicked {

    private lateinit var viewModel: LibraryViewModel


    override fun getLayout() = R.layout.fragment_common_content

    override fun setupClickListener() {
        binding.close.setOnClickListener {
            dismiss()
        }
    }

    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
        initViewModel()

        setRecyclerMostCommon(viewModel.mLibraryContentMostCommon)
    }

    private fun initViewModel() {
        val libraryDao = AppDatabase.getDatabase(requireContext()).libraryDao()
        val libraryContentRepo = LibraryContentRepo(libraryDao)
        val getLibraryContentUseCase = GetLibraryContentUseCase(libraryContentRepo)
        val factory = LibraryViewModelFactory(sharedPreferences, getLibraryContentUseCase,activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[LibraryViewModel::class.java]
    }

    private fun setRecyclerMostCommon(libraryContent: List<LibraryContent>?) {

        binding.recyclerMostCommon.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = CommonContentLibraryAdapter(
                libraryContent,
                requireActivity(),
                sharedPreferences,
                this@CommonContentFragment
            )
        }
    }


    override fun onItemClicked(type: String, index: Int, content: String) {
        updateCurrentIndex(index)
        updateCurrentContent(content)
        when (type) {
            ARTICLE -> findNavController().navigate(R.id.action_libraryContentFragment_to_articleFragment)
            VIDEO -> findNavController().navigate(R.id.action_libraryContentFragment_to_videoFragment)
//            AUDIO -> findNavController().navigate(R.id.action_libraryContentFragment_to_audioFragment)
        }
        dismiss()
    }

    private fun updateCurrentIndex(index: Int) {
        viewModel.setCurrentContentIndex(index)
    }

    private fun updateCurrentContent(content: String) {
        viewModel.setCurrentContentContent(content)
    }


}