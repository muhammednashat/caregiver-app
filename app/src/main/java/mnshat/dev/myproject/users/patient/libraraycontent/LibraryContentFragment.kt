package mnshat.dev.myproject.users.patient.libraraycontent

import androidx.recyclerview.widget.LinearLayoutManager
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.CommonContentLibraryAdapter
import mnshat.dev.myproject.adapters.CustomizedContentLibraryAdapter
import mnshat.dev.myproject.databinding.FragmentLibraryContentBinding
import mnshat.dev.myproject.model.LibraryContent


class LibraryContentFragment : BaseLibraryFragment<FragmentLibraryContentBinding>() {


    override fun getLayout() = R.layout.fragment_library_content

    override fun initializeViews() {
        super.initializeViews()
        observeViewModel()
        showProgressDialog()
        viewModel.retrieveLibraryContent()
    }

    override fun setupClickListener() {
        super.setupClickListener()
        binding.icBack.setOnClickListener {
            activity?.finish()
        }
        binding.showAllCustomized.setOnClickListener {
            navigateToCustomizedContent(viewModel.mLibraryContentCustomized.toTypedArray(),getString(R.string.customized_for_you))
        }
        binding.showAllCommon.setOnClickListener {
            CommonContentFragment().show(
                childFragmentManager,
                CommonContentFragment::class.java.name
            )
        }
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

}
