package mnshat.dev.myproject.getLibraryContent.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import mnshat.dev.myproject.R
import mnshat.dev.myproject.getLibraryContent.presentaion.CommonContentLibraryAdapter
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentCommonContentBinding
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.log


class CommonContentFragment : BaseBottomSheetDialogFragment(),
    OnItemLibraryContentClicked {

    private val viewModel: LibraryViewModel by activityViewModels()
    private lateinit var binding: FragmentCommonContentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommonContentBinding.inflate(inflater, container, false)
        setupClickListener()
        initializeViews()
        return binding.root
    }


    private fun setupClickListener() {
        binding.close.setOnClickListener {
            dismiss()
        }
    }

     private fun initializeViews() {
        setRecyclerMostCommon(viewModel.mLibraryContentMostCommon)
    }

    private fun setRecyclerMostCommon(libraryContent: List<LibraryContent>?) {

        binding.recyclerMostCommon.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = CommonContentLibraryAdapter(
                libraryContent,
                requireActivity(),
                viewModel.sharedPreferences,
                this@CommonContentFragment
            )
        }
    }


    override fun onItemClicked(type: String, index: Int, content: String) {
//        log("onItemClicked")
//        log(content)
//        log("index $index")
//        log("type $type")

        updateCurrentIndex(index)
        updateCurrentContent(content)
        when (type) {
            ARTICLE -> findNavController().navigate(R.id.action_libraryContentFragment_to_articleFragment)

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