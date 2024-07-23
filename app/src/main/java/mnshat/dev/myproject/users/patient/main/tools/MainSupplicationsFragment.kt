package mnshat.dev.myproject.users.patient.main.tools

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentMainSupplicationsBinding


class MainSupplicationsFragment : BaseFragment<FragmentMainSupplicationsBinding>() {

    override fun setupClickListener() {

        binding.supplication.setOnClickListener{
            findNavController().navigate(R.id.action_mainAzcarFragment_to_userSupplicationsFragment)
        }

    }

    override fun initializeViews() {

    }

    override fun getLayout() = R.layout.fragment_main_supplications

}