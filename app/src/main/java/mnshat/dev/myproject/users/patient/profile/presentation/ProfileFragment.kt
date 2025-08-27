package mnshat.dev.myproject.users.patient.profile.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentProfileBinding
import mnshat.dev.myproject.users.patient.supporters.presentation.SupportersActivity
import mnshat.dev.myproject.util.loadImage

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private  lateinit var binding: FragmentProfileBinding
   private val viwModel:ProfileViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        setupClickListener()
        return  binding.root
    }


    override fun onStart() {
        super.onStart()
        binding.nameUser.text = viwModel.userProfile().name
        loadImage(requireActivity(),viwModel.userProfile().imageUser,binding.imageUser)
    }

     fun setupClickListener() {
        binding.editProfile.setOnClickListener {
            startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
        }

        binding.sharedContent.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_postsFragment2)
        }
         binding.reports.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_trackingMoodFragment)
        }
         binding.services.myPoints.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_myPointsFragment)
        }
        binding.services.supporter.setOnClickListener {
            startActivity(Intent(requireActivity(), SupportersActivity::class.java))
        }

        binding.services.emergencyHelp.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_numberHelpingFragment2)
        }

        binding.settings.setOnClickListener {
         findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
        }
        binding.gratitude.setOnClickListener {
           findNavController().navigate(R.id.action_profileFragment_to_gratitudeListFragment)
        }
        binding.logOut.setOnClickListener {
            showDialogConfirmLogout(viwModel.sharedPreferences)
        }
    }





}