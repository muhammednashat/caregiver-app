package mnshat.dev.myproject.users.patient.intro

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentIntro2Binding
import mnshat.dev.myproject.users.patient.main.BaseUserFragment

class Intro2Fragment : BaseUserFragment<FragmentIntro2Binding>() {

    override fun getLayout()= R.layout.fragment_intro2

    override fun initializeViews() {
    }

    override fun setupClickListener() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_intro2Fragment_to_intro3Fragment)
        }
        binding.btnPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}