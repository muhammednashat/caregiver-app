package mnshat.dev.myproject.users.patient.profile.editprofile

import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentSettingsBinding
import mnshat.dev.myproject.util.ENGLISH_KEY

class SettingsFragment : BaseEditProfileFragment<FragmentSettingsBinding>() {

    override fun getLayout() = R.layout.fragment_settings
    override fun initializeViews() {

        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }

    }

}