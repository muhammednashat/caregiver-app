package mnshat.dev.myproject.users.caregiver.tools

import android.content.Intent
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCaregiverToolsBinding
import mnshat.dev.myproject.users.caregiver.main.BaseCaregiverFragment
import mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion.SupportCofeActivity
import mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion.CofeActivity


class CaregiverToolsFragment : BaseCaregiverFragment<FragmentCaregiverToolsBinding>() {

    override fun getLayout()= R.layout.fragment_caregiver_tools


    override fun setupClickListener() {
        super.setupClickListener()

      binding.strengthBuildingPlan.setOnClickListener {
       findNavController().navigate(R.id.action_caregiverToolsFragment_to_strengthBuildingPlanFragment)
      }

        binding.cofe.setOnClickListener{
            startActivity(Intent(requireContext(), SupportCofeActivity::class.java))
        }
        binding.supporterCompass.setOnClickListener {
       findNavController().navigate(R.id.action_caregiverToolsFragment_to_supporterCompassFragment)
     }

     binding.imageView16.setOnClickListener {
         findNavController().popBackStack()
     }

    }


}