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
        binding.icBack.setOnClickListener {
         findNavController().popBackStack()
        }
    }

    override fun initializeViews() {
        showProgressDialog()
        FirebaseService.retrieveUser(null, FirebaseService.userEmail!!) { user ->
            user?.gratitudeList?.let {
                val adapter = GratitudeAdapter(it, getGratitudeQuestionsList(requireActivity()))
                binding.recyclerViewGratitude.adapter = adapter
                dismissProgressDialog()
            } ?: showView()

            user?.let {

            }
        }
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }

    }

    private fun showView() {
        dismissProgressDialog()
        binding.noItems.visibility = View.VISIBLE
    }
}
