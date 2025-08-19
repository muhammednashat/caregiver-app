package mnshat.dev.myproject.getLibraryContent.presentaion

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.CustomizedContentLibraryAdapter
import mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion.CustomizedContentFragmentArgs
import mnshat.dev.myproject.databinding.FragmentCustomizedContentBinding
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.AUDIO
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.VIDEO


class CustomizedContentFragment : BaseLibraryFragment<FragmentCustomizedContentBinding>() {


    override fun getLayout() = R.layout.fragment_customized_content

    override fun initializeViews() {
        super.initializeViews()
        retrieveDataFromArguments()
    }


    private fun intiBackgroundBasedOnLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
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
                sharedPreferences,
                this@CustomizedContentFragment
            )
        }
    }

    override fun setupClickListener() {
        super.setupClickListener()

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onItemClicked(type: String, index: Int, content: String) {
        super.onItemClicked(type, index, content)

        when (type) {
            ARTICLE -> findNavController().navigate(R.id.action_customizedContentFragment_to_articleFragment)
            VIDEO -> findNavController().navigate(R.id.action_customizedContentFragment_to_videoFragment)
            AUDIO -> findNavController().navigate(R.id.action_customizedContentFragment_to_audioFragment)

        }
    }

}