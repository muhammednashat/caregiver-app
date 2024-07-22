package mnshat.dev.myproject.users.caregiver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCaregiverHomeBinding
import mnshat.dev.myproject.databinding.FragmentCaregiverProfileBinding
import mnshat.dev.myproject.util.NAME_PARTNER
import mnshat.dev.myproject.util.USER_NAME


class CaregiverProfileFragment : BaseCaregiverFragment<FragmentCaregiverProfileBinding>() {


    override fun initializeViews() {

     }

    override fun getLayout() = R.layout.fragment_caregiver_profile
}