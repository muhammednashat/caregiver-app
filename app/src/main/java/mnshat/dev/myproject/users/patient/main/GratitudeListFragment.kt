package mnshat.dev.myproject.users.patient.main

import android.view.View
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.GratitudeAdapter
import mnshat.dev.myproject.databinding.FragmentGratitudeListBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.getGratitudeQuestionsList

class GratitudeListFragment : BasePatientFragment<FragmentGratitudeListBinding>() {


    override fun getLayout() = R.layout.fragment_gratitude_list


    override fun setupClickListener() {
        binding.btnAddGratitude.setOnClickListener {
         findNavController().navigate(R.id.action_gratitudeListFragment_to_gratitudeFragment)
        }
        binding.icBack.setOnClickListener {
         findNavController().popBackStack()
        }
    }

    override fun initializeViews() {
        retrieveGratitudeList()
    }

    private fun retrieveGratitudeList(){
        showProgressDialog()
        FirebaseService.retrieveUser(null, FirebaseService.userEmail!!) { user ->
            user?.gratitudeList?.let {
                val adapter = GratitudeAdapter(it, getGratitudeQuestionsList(requireActivity()))
                binding.recyclerViewGratitude.adapter = adapter
            } ?: showNoItemsView()

            dismissProgressDialog()

        }
    }

    private fun showNoItemsView() {
        binding.gratitudeList.visibility = View.GONE
        binding.noItems.visibility = View.VISIBLE
    }
}
