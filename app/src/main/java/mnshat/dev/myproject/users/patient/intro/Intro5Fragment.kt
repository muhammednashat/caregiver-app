package mnshat.dev.myproject.users.patient.intro

import android.content.Intent
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentIntro5Binding
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.users.patient.main.UserScreensActivity
import mnshat.dev.myproject.users.patient.editprofile.EditProfileActivity


class Intro5Fragment : BasePatientFragment<FragmentIntro5Binding>() {

    override fun getLayout()= R.layout.fragment_intro5

    override fun initializeViews() {
    }

    override fun setupClickListener() {

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
            requireActivity().finish()
        }
        binding.btnDailyProgram.setOnClickListener {
            startActivity(Intent(requireActivity(), UserScreensActivity::class.java))
            requireActivity().finish()
        }
        
    }





}