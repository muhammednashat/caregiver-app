package mnshat.dev.myproject.getLibraryContent.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentCustomizedContentBinding
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.util.ARTICLE
import kotlin.getValue


class CustomizedContentFragment : BaseFragment(), OnItemLibraryContentClicked {


    private  val viewModel: LibraryViewModel  by activityViewModels()
    private lateinit var binding: FragmentCustomizedContentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCustomizedContentBinding.inflate(inflater, container, false)
        retrieveDataFromArguments()
        setupClickListener()
        return binding.root
    }




    private fun retrieveDataFromArguments() {
        val args: CustomizedContentFragmentArgs by navArgs()
        binding.textTitle.text = args.textTitle
        setupUserRecyclerView(args.libraryContentList.toList())
    }

    private fun setupUserRecyclerView(libraryContent: List<LibraryContent>) {
        binding.recyclerCustomized.apply {
            adapter = CustomizedContentLibraryAdapter(
                libraryContent,
                requireActivity(),
                viewModel.sharedPreferences,
                this@CustomizedContentFragment
            )
        }
    }

    private fun setupClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onItemClicked(type: String, index: Int, content: String) {
        viewModel.setCurrentContentIndex(index)
        viewModel.setCurrentContentContent(content)
        when (type) {
            ARTICLE -> findNavController().navigate(R.id.action_customizedContentFragment_to_articleFragment)
        }
    }
}