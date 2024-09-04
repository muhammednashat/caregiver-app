package mnshat.dev.myproject.users.patient.libraraycontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.CustomizedContentLibraryAdapter
import mnshat.dev.myproject.databinding.FragmentCustomizedContentBinding
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.users.patient.main.tools.supplications.AddSupplicationsFragment
import mnshat.dev.myproject.users.patient.main.tools.supplications.UserSupplicationsFragmentArgs
import mnshat.dev.myproject.util.ENGLISH_KEY


class CustomizedContentFragment  : BasePatientFragment<FragmentCustomizedContentBinding>() {
    override fun getLayout()  = R.layout.fragment_customized_content

    override fun initializeViews() {
        super.initializeViews()
        intiBackgroundBasedOnLang()
        retrieveDataFromArguments()
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
         adapter = CustomizedContentLibraryAdapter(libraryContent,requireActivity(),sharedPreferences)
     }
    }
    override fun setupClickListener() {
        super.setupClickListener()

        binding.icBack.setOnClickListener{
            findNavController().popBackStack()
        }

    }
}