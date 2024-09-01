package mnshat.dev.myproject.users.patient.libraraycontent

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.LibraryContentAdapter
import mnshat.dev.myproject.databinding.FragmentEducationalContentBinding
import mnshat.dev.myproject.factories.LibraryViewModelFactory
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.users.patient.main.BasePatientFragment


class LibraryContentFragment : BasePatientFragment<FragmentEducationalContentBinding>() {

    private lateinit var viewModel:LibraryViewModel

    override fun getLayout() = R.layout.fragment_educational_content

    override fun initializeViews() {
        super.initializeViews()
        initViewModel()
        observeViewModel()
        showProgressDialog()
        viewModel.retrieveLibraryContent()
    }




    private fun initViewModel() {
        val factory = LibraryViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[LibraryViewModel::class.java]
    }
    private fun observeViewModel() {

        viewModel.libraryContentMostCommon.observe(viewLifecycleOwner) { libraryContent ->
            setRecyclerMostCommon(libraryContent)
            dismissProgressDialog()
        }

        viewModel.libraryContentCustomized.observe(viewLifecycleOwner) { libraryContent ->
            setRecyclerCustomized(libraryContent)
            dismissProgressDialog()
        }



    }

    private fun setRecyclerMostCommon(libraryContent: List<LibraryContent>?) {

        binding.recyclerMostCommon.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = LibraryContentAdapter(libraryContent,requireActivity(),sharedPreferences)
    }
    }

    private fun setRecyclerCustomized(libraryContent: List<LibraryContent>?) {

        binding.recyclerCustomized.apply {
//            adapter = LibraryContentAdapter(libraryContent)
    }


}
}
