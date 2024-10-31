package mnshat.dev.myproject.commonFeatures.sharingcontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.commonFeatures.libraraycontent.LibraryViewModel
import mnshat.dev.myproject.databinding.FragmentSharingListBinding
import mnshat.dev.myproject.factories.LibraryViewModelFactory
import mnshat.dev.myproject.factories.SharingViewModelFactory
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY


class SharingListFragment : BasePatientFragment<FragmentSharingListBinding>() {

    lateinit var viewModel: SharingViewModel


    override fun getLayout() = R.layout.fragment_sharing_list


    override fun setupClickListener() {

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initializeViews() {
        initViewModel()
        retrieveSharedList()

        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }

    }

    private fun retrieveSharedList() {
     viewModel.retrieveSharedList(){ list, error ->

     }
    }

    private fun initViewModel() {
        val factory = SharingViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[SharingViewModel::class.java]
    }

}