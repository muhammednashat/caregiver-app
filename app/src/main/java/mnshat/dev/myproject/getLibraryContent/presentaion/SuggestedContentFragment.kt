package mnshat.dev.myproject.getLibraryContent.presentaion


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentSuggestedContentBinding
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import kotlin.getValue

class SuggestedContentFragment : BaseBottomSheetDialogFragment(),
    OnItemLibraryContentClicked {

    private lateinit var binding: FragmentSuggestedContentBinding
    private lateinit var title: String
    private lateinit var type: String
    private val viewModel: LibraryViewModel by activityViewModels()
    private lateinit var mOnItemLibraryContentClicked: OnItemLibraryContentClicked


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      binding = FragmentSuggestedContentBinding.inflate(inflater, container, false)
        initializeViews()
        setupClickListener()
        return  binding.root

    }



    private fun initializeViews() {
        binding.title.text = title
        setRecyclerMostCommon(viewModel.getContentsBasedType(type))
    }

    private fun setupClickListener() {
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



    private fun setRecyclerMostCommon(libraryContent: List<LibraryContent>?) {

        binding.recyclerView.apply {
            adapter = CustomizedContentLibraryAdapter(
                libraryContent,
                requireActivity(),
                viewModel.sharedPreferences,
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