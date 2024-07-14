package mnshat.dev.myproject.users.patient.intro

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentIntro4Binding
import mnshat.dev.myproject.users.patient.main.BaseUserFragment



class Intro4Fragment : BaseUserFragment<FragmentIntro4Binding>() {

    override fun getLayout()= R.layout.fragment_intro4

    override fun initializeViews() {
    }

    override fun setupClickListener() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_intro4Fragment_to_intro5Fragment)
        }
        binding.btnPrevious.setOnClickListener {
            findNavController().popBackStack()
        }
    }





}