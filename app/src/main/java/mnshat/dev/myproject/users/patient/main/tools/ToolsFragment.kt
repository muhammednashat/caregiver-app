package mnshat.dev.myproject.users.patient.main.tools

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentToolsBinding


class ToolsFragment : BaseFragment<FragmentToolsBinding>() {

    override fun setupClickListener() {

        binding.imageSupplications.setOnClickListener{
            findNavController().navigate(R.id.action_mainAzcarFragment_to_mainSupplicationsFragment)
        }

    }

    override fun initializeViews() {

    }

    override fun getLayout() = R.layout.fragment_tools

}