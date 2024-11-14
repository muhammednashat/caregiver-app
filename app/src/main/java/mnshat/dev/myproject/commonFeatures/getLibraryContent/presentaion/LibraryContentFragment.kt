package mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.CommonContentLibraryAdapter
import mnshat.dev.myproject.adapters.CustomizedContentLibraryAdapter
import mnshat.dev.myproject.databinding.FragmentLibraryContentBinding
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.AUDIO
import mnshat.dev.myproject.util.VIDEO
import mnshat.dev.myproject.util.errorSnackBar
import mnshat.dev.myproject.util.isInternetAvailable


class LibraryContentFragment : BaseLibraryFragment<FragmentLibraryContentBinding>() {


    override fun getLayout() = R.layout.fragment_library_content

    override fun initializeViews() {
        super.initializeViews()
        observeViewModel()
       if (!isInternetAvailable(requireActivity())){
           binding.noInternet.visibility = View.VISIBLE
           binding.constraintLayout14.visibility = View.GONE
           errorSnackBar(requireView(),getString(R.string.no_internet))
       }else{
           showProgressDialog()
//           viewModel.retrieveLibraryContent()

       }
    }

    private fun  getLibraryContent(){

    }


    override fun setupClickListener() {
        super.setupClickListener()
        binding.icBack.setOnClickListener {
            activity?.finish()
        }
        binding.showAllCustomized.setOnClickListener {
            navigateToCustomizedContent(
                viewModel.mLibraryContentCustomized.toTypedArray(),
                getString(R.string.customized_for_you)
            )
        }
        binding.showAllCommon.setOnClickListener {
            CommonContentFragment().show(
                childFragmentManager,
                CommonContentFragment::class.java.name
            )
        }
    }

    private fun navigateToCustomizedContent(contents: Array<LibraryContent>, textTitle: String) {
        val action = LibraryContentFragmentDirections
            .actionLibraryContentFragmentToCustomizedContentFragment(contents, textTitle)
        findNavController().navigate(action)
    }

    private fun observeViewModel() {
        viewModel.isReadyDisplay.observe(viewLifecycleOwner) {
            if (it) {
                setRecyclerCustomized(viewModel.mLibraryContentCustomized)
                setRecyclerMostCommon(viewModel.mLibraryContentMostCommon)
                dismissProgressDialog()
                viewModel.resetIsReadyDisplay()
            }
        }
    }

    private fun setRecyclerMostCommon(libraryContent: List<LibraryContent>?) {
        binding.recyclerMostCommon.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            adapter = CommonContentLibraryAdapter(
                libraryContent,
                requireActivity(),
                sharedPreferences,
                this@LibraryContentFragment
            )
        }
    }

    private fun setRecyclerCustomized(libraryContent: List<LibraryContent>?) {

        binding.recyclerCustomized.apply {
            adapter = CustomizedContentLibraryAdapter(
                libraryContent,
                requireActivity(),
                sharedPreferences,
                this@LibraryContentFragment
            )
        }
    }

    override fun onItemClicked(type: String, index: Int, content: String) {

        super.onItemClicked(type, index, content)
        when (type) {
            ARTICLE -> findNavController().navigate(R.id.action_libraryContentFragment_to_articleFragment)
            VIDEO -> findNavController().navigate(R.id.action_libraryContentFragment_to_videoFragment)
            AUDIO -> findNavController().navigate(R.id.action_libraryContentFragment_to_audioFragment)
        }
    }

}
