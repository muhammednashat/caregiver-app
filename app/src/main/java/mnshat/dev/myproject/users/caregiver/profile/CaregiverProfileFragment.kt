package mnshat.dev.myproject.users.caregiver.profile

import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCaregiverProfileBinding
import mnshat.dev.myproject.users.caregiver.main.BaseCaregiverFragment


class CaregiverProfileFragment : BaseCaregiverFragment<FragmentCaregiverProfileBinding>() {


    override fun initializeViews() {

     }

    override fun setupClickListener() {
        super.setupClickListener()


    binding.signout.setOnClick()
// complete sign out then//
        // navigate to user and retrive all supports to start message them

    }
    override fun getLayout() = R.layout.fragment_caregiver_profile
}