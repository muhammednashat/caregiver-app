package mnshat.dev.myproject.users.caregiver.profile

import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCaregiverProfileBinding
import mnshat.dev.myproject.users.caregiver.main.BaseCaregiverFragment
import mnshat.dev.myproject.util.USER_IMAGE
import mnshat.dev.myproject.util.USER_NAME
import mnshat.dev.myproject.util.loadImage


class CaregiverProfileFragment : BaseCaregiverFragment<FragmentCaregiverProfileBinding>() {
    override fun getLayout() = R.layout.fragment_caregiver_profile


    override fun initializeViews() {
        super.initializeViews()
        binding.nameUser.text = sharedPreferences.getString(USER_NAME)
        loadImage(requireActivity(),sharedPreferences.getString(USER_IMAGE),binding.imageUser)

    }

    override fun setupClickListener() {
        super.setupClickListener()
    binding.signOut.setOnClickListener{
        showDialogConfirmLogout()
    }

    }
}