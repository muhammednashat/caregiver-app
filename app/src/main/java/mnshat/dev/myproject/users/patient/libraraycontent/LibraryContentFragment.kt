package mnshat.dev.myproject.users.patient.libraraycontent

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.CommonContentLibraryAdapter
import mnshat.dev.myproject.adapters.CustomizedContentLibraryAdapter
import mnshat.dev.myproject.auth.AgeFragment
import mnshat.dev.myproject.databinding.FragmentLibraryContentBinding
import mnshat.dev.myproject.factories.LibraryViewModelFactory
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.users.patient.main.tools.supplications.MainSupplicationsFragmentDirections
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.log


class LibraryContentFragment : BasePatientFragment<FragmentLibraryContentBinding>(),
    OnItemLibraryContentClicked {

    private lateinit var viewModel: LibraryViewModel

    override fun getLayout() = R.layout.fragment_library_content

    override fun initializeViews() {
        super.initializeViews()
        initViewModel()
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
            navigateToCustomizedContent()
        }
        binding.showAllCommon.setOnClickListener {
            CommonContentFragment().show(
                childFragmentManager,
                CommonContentFragment::class.java.name
            )
        }
    }

    private fun navigateToCustomizedContent() {
        val action = LibraryContentFragmentDirections
            .actionLibraryContentFragmentToCustomizedContentFragment(viewModel.mLibraryContentCustomized.toTypedArray())
        findNavController().navigate(action)
    }


    private fun initViewModel() {
        val factory = LibraryViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[LibraryViewModel::class.java]
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

    override fun onItemClicked(type: String, index: Int) {
        updateCurrentIndex(index)
        log("$type    $index")
        when (type) {
            ARTICLE -> findNavController().navigate(R.id.action_libraryContentFragment_to_articleFragment)
        }
    }

    private fun updateCurrentIndex(index: Int) {
        viewModel.setCurrentContentIndex(index)
    }


}
