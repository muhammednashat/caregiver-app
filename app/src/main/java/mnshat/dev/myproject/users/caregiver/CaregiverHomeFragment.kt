package mnshat.dev.myproject.users.caregiver

import android.content.Intent
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCaregiverHomeBinding
import mnshat.dev.myproject.users.libraraycontent.LibraryActivity
import mnshat.dev.myproject.util.log

class CaregiverHomeFragment : BaseCaregiverFragment<FragmentCaregiverHomeBinding>() {



    override fun getLayout()= R.layout.fragment_caregiver_home

    override fun setupClickListener() {

        super.setupClickListener()
        binding.enter.setOnClickListener {
            findNavController().navigate(R.id.action_caregiverHomeFragment_to_usersFragment)
        }

        binding.rootEducationalContent.setOnClickListener {
            startActivity(Intent(requireActivity(), LibraryActivity::class.java))
        }

    }

}