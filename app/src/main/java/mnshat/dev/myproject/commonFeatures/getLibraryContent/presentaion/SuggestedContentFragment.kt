package mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion


import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.CustomizedContentLibraryAdapter
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment2
import mnshat.dev.myproject.commonFeatures.getLibraryContent.data.LibraryContentRepo
import mnshat.dev.myproject.databinding.FragmentSuggestedContentBinding
import mnshat.dev.myproject.factories.LibraryViewModelFactory
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.useCase.GetLibraryContentUseCase
import mnshat.dev.myproject.dataSource.room.AppDatabase
import mnshat.dev.myproject.util.ENGLISH_KEY

class SuggestedContentFragment : BaseBottomSheetDialogFragment2<FragmentSuggestedContentBinding>(),

    OnItemLibraryContentClicked {
    private lateinit var title: String
    private lateinit var type: String
    private lateinit var viewModel: LibraryViewModel
    private lateinit var mOnItemLibraryContentClicked: OnItemLibraryContentClicked

    override fun getLayout() = R.layout.fragment_suggested_content

    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
        binding.title.text = title
        initViewModel()
        setRecyclerMostCommon(viewModel.getContentsBasedType(type))
    }

    override fun setupClickListener() {
        super.setupClickListener()
        binding.close.setOnClickListener {
            dismiss()
        }
    }

    fun setTitle(title: String): SuggestedContentFragment {
        this.title = title
        return this

    }

    fun setType(type: String): SuggestedContentFragment {
        this.type = type
        return this
    }

    private fun initViewModel() {
        val libraryDao = AppDatabase.getDatabase(requireContext()).libraryDao()
        val libraryContentRepo = LibraryContentRepo(libraryDao)
        val getLibraryContentUseCase = GetLibraryContentUseCase(libraryContentRepo)
        val factory = LibraryViewModelFactory(sharedPreferences, getLibraryContentUseCase,activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[LibraryViewModel::class.java]
    }

    private fun setRecyclerMostCommon(libraryContent: List<LibraryContent>?) {

        binding.recyclerView.apply {

            adapter = CustomizedContentLibraryAdapter(
                libraryContent,
                requireActivity(),
                sharedPreferences,
                this@SuggestedContentFragment
            )
        }
    }

    override fun onItemClicked(type: String, index: Int, content: String) {
        dismiss()
        mOnItemLibraryContentClicked.onItemClicked(type, index, content)
    }

    fun setOnItemLibraryContent(onItemLibraryContentClicked: OnItemLibraryContentClicked): SuggestedContentFragment {
        mOnItemLibraryContentClicked = onItemLibraryContentClicked
        return this

    }

}