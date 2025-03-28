package mnshat.dev.myproject.users.patient.tools

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment2
import mnshat.dev.myproject.databinding.FragmentToolsBinding


class ToolsFragment : BaseFragment2<FragmentToolsBinding>() {

    override fun setupClickListener() {


        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imageSupplications.setOnClickListener{
            findNavController().navigate(R.id.action_toolsFragment_to_mainSupplicationsFragment)
        }
        binding.imageBreath.setOnClickListener{
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