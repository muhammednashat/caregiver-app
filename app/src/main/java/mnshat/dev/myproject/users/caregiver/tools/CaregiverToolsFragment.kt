package mnshat.dev.myproject.users.caregiver.tools

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCaregiverToolsBinding
import mnshat.dev.myproject.users.caregiver.main.BaseCaregiverFragment


class CaregiverToolsFragment : BaseCaregiverFragment<FragmentCaregiverToolsBinding>() {

    override fun getLayout()= R.layout.fragment_caregiver_tools


    override fun setupClickListener() {
        super.setupClickListener()

      binding.strengthBuildingPlan.setOnClickListener {
       findNavController().navigate(R.id.action_caregiverToolsFragment_to_strengthBuildingPlanFragment)
     }
        binding.supporterCompass.setOnClickListener {
       findNavController().navigate(R.id.action_caregiverToolsFragment_to_supporterCompassFragment)
     }
     binding.imageView16.setOnClickListener {
         findNavController().popBackStack()
     }

    }


}