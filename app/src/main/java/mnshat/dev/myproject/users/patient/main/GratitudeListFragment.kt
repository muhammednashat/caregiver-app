package mnshat.dev.myproject.users.patient.main

import android.view.View
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentGratitudeListBinding
import mnshat.dev.myproject.firebase.FirebaseService

class GratitudeListFragment : BasePatientFragment<FragmentGratitudeListBinding>() {


    override fun getLayout() = R.layout.fragment_gratitude_list
    override fun setupClickListener() {

    }

    override fun initializeViews() {
        showProgressDialog()
        FirebaseService.retrieveUser(null, FirebaseService.userEmail!!) { user ->
            user?.gratitudeList?.let {
                val adapter = GratitudeAdapter(it)
                binding.recyclerViewGratitude.adapter = adapter
                dismissProgressDialog()
            } ?: showView()

            user?.let {

            }
        }


    }

    private fun showView() {
        dismissProgressDialog()
        binding.noItems.visibility = View.VISIBLE
    }
}
