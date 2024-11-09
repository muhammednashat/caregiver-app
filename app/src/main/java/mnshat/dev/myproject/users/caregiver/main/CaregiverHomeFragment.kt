package mnshat.dev.myproject.users.caregiver.main

import android.content.Intent
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.commonFeatures.libraraycontent.LibraryActivity
import mnshat.dev.myproject.databinding.FragmentCaregiverHomeBinding
import mnshat.dev.myproject.util.USER_IMAGE
import mnshat.dev.myproject.util.USER_NAME
import mnshat.dev.myproject.util.loadImage

class CaregiverHomeFragment : BaseCaregiverFragment<FragmentCaregiverHomeBinding>() {



    override fun getLayout()= R.layout.fragment_caregiver_home


    override fun initializeViews() {
        super.initializeViews()
        binding.nameUser.text = sharedPreferences.getString(USER_NAME)
        loadImage(requireActivity(),sharedPreferences.getString(USER_IMAGE),binding.imageUser)

    }
    override fun setupClickListener() {

        super.setupClickListener()

        binding.enter.setOnClickListener {
            findNavController().navigate(R.id.action_caregiverHomeFragment_to_usersFragment)
        }
        binding.posts.setOnClickListener {
         findNavController().navigate(R.id.action_caregiverHomeFragment_to_postsFragment2)
        }
        binding.rootTools.setOnClickListener {
         findNavController().navigate(R.id.action_caregiverHomeFragment_to_caregiverToolsFragment)
        }
        binding.helpNumbers.setOnClickListener {
            findNavController().navigate(R.id.action_caregiverHomeFragment_to_numberHelpingFragment)
        }

        binding.rootEducationalContent.setOnClickListener {
            startActivity(Intent(requireActivity(), LibraryActivity::class.java))
        }

    }

}