package mnshat.dev.myproject.getLibraryContent.presentaion

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.CommonContentLibraryAdapter
import mnshat.dev.myproject.adapters.CustomizedContentLibraryAdapter
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.LibraryContentFragmentDirections
import mnshat.dev.myproject.databinding.FragmentLibraryContentBinding
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.AUDIO
import mnshat.dev.myproject.util.VIDEO

@AndroidEntryPoint
class LibraryContentFragment : BaseFragment(),OnItemLibraryContentClicked {

    private  val viewModel: LibraryViewModel  by activityViewModels()
    private lateinit var binding: FragmentLibraryContentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLibraryContentBinding.inflate(inflater, container, false)
        checkInternetConnection()
        return binding.root
    }
    private fun checkInternetConnection() {
        if (isConnected()) {
            initializeViews()
        } else {
            showNoInternetDialog()
        }
    }
    private fun showNoInternetDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.no_internet_connection))
            .setMessage(getString(R.string.please_check_your_internet_connection_and_try_again))
            .setPositiveButton(getString(R.string.try_again)) { dialog, _ ->
                checkInternetConnection()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun initializeViews() {
        showProgressDialog()
        viewModel.retrieveLibraryContent()
        observeViewModel()
        setupClickListener()
    }




    private fun setupClickListener() {
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
                viewModel.sharedPreferences,
                this@LibraryContentFragment
            )
        }

    }

    private fun setRecyclerCustomized(libraryContent: List<LibraryContent>?) {

        binding.recyclerCustomized.apply {
            adapter = CustomizedContentLibraryAdapter(
                libraryContent,
                requireActivity(),
                viewModel.sharedPreferences,
                this@LibraryContentFragment
            )
        }
    }

    override fun onItemClicked(type: String, index: Int, content: String) {

        when (type) {
            ARTICLE -> findNavController().navigate(R.id.action_libraryContentFragment_to_articleFragment)
            VIDEO -> findNavController().navigate(R.id.action_libraryContentFragment_to_videoFragment)
            AUDIO -> findNavController().navigate(R.id.action_libraryContentFragment_to_audioFragment)
        }
    }

}
