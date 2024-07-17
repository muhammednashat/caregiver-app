package mnshat.dev.myproject.users.patient.intro

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentIntro1Binding
import mnshat.dev.myproject.users.patient.main.BasePatientFragment


class Intro1Fragment : BasePatientFragment<FragmentIntro1Binding>() {

    override fun getLayout()= R.layout.fragment_intro1

    override fun initializeViews() {
    }

    override fun setupClickListener() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_intro1Fragment_to_intro2Fragment)
        }
    }





}