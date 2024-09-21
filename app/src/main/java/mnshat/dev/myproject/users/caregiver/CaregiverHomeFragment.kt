package mnshat.dev.myproject.users.caregiver

import android.content.Intent
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCaregiverHomeBinding
import mnshat.dev.myproject.users.patient.libraraycontent.LibraryActivity

class CaregiverHomeFragment : BaseCaregiverFragment<FragmentCaregiverHomeBinding>() {



    override fun initializeViews() {

     }

    override fun getLayout()= R.layout.fragment_caregiver_home

    override fun setupClickListener() {
        super.setupClickListener()

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_caregiverHomeFragment_to_usersFragment)
        }
        //
        binding.rootEducationalContent.setOnClickListener {
            startActivity(Intent(requireActivity(), LibraryActivity::class.java))
        }

    }

}