package mnshat.dev.myproject.users.patient.tools

import android.content.Intent
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment2
import mnshat.dev.myproject.databinding.FragmentToolsBinding
import mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion.CofeActivity


class ToolsFragment : BaseFragment2<FragmentToolsBinding>() {

    override fun setupClickListener() {

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imageCofe.setOnClickListener {
            startActivity(Intent(requireContext(), CofeActivity::class.java))
        }
        binding.imageSupplications.setOnClickListener {
            findNavController().navigate(R.id.action_toolsFragment_to_mainSupplicationsFragment)
        }
        binding.imageBreath.setOnClickListener {
            findNavController().navigate(R.id.action_toolsFragment_to_mainBreathFragment)
        }
        binding.imageGratitude.setOnClickListener {
            findNavController().navigate(R.id.action_toolsFragment_to_gratitudeFragment)

        }

    }

    override fun initializeViews() {

    }

    override fun getLayout() = R.layout.fragment_tools

}