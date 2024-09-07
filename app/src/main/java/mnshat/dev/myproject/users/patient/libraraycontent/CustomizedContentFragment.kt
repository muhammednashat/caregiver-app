package mnshat.dev.myproject.users.patient.libraraycontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.CustomizedContentLibraryAdapter
import mnshat.dev.myproject.databinding.FragmentCustomizedContentBinding
import mnshat.dev.myproject.factories.LibraryViewModelFactory
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.users.patient.main.tools.supplications.AddSupplicationsFragment
import mnshat.dev.myproject.users.patient.main.tools.supplications.UserSupplicationsFragmentArgs
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.AUDIO
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.VIDEO


class CustomizedContentFragment  : BasePatientFragment<FragmentCustomizedContentBinding>(),
    OnItemLibraryContentClicked {
    private lateinit var viewModel:LibraryViewModel


    override fun getLayout()  = R.layout.fragment_customized_content

    override fun initializeViews() {
        super.initializeViews()
        intiBackgroundBasedOnLang()
        retrieveDataFromArguments()
        initViewModel()

    }
    private fun initViewModel() {
        val factory = LibraryViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[LibraryViewModel::class.java]
    }

    private fun intiBackgroundBasedOnLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }

    private fun retrieveDataFromArguments() {

        val args: CustomizedContentFragmentArgs by navArgs()
        setupUserRecyclerView(args.libraryContentList.toList())
    }

    private fun setupUserRecyclerView(libraryContent: List<LibraryContent>) {
     binding.recyclerCustomized.apply {
         adapter = CustomizedContentLibraryAdapter(libraryContent,requireActivity(),sharedPreferences,this@CustomizedContentFragment)
     }
    }
    override fun setupClickListener() {
        super.setupClickListener()

        binding.icBack.setOnClickListener{
            findNavController().popBackStack()
        }

    }

    override fun onItemClicked(type: String, index: Int,content:String) {
        updateCurrentIndex(index)
        updateCurrentContent(content)
        when (type) {
            ARTICLE -> findNavController().navigate(R.id.action_customizedContentFragment_to_articleFragment)
            VIDEO -> findNavController().navigate(R.id.action_customizedContentFragment_to_videoFragment)
            AUDIO -> findNavController().navigate(R.id.action_customizedContentFragment_to_audioFragment)
        }
    }

    private fun updateCurrentIndex(index: Int) {
        viewModel.setCurrentContentIndex(index)
    }
    private fun updateCurrentContent(content: String) {
        viewModel.setCurrentContentContent(content)
    }

}