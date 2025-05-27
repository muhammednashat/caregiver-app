package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentWhatShouldDoBinding

class WhatShouldDoFragment : Fragment() {

private lateinit var binding: FragmentWhatShouldDoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

          binding = FragmentWhatShouldDoBinding.inflate(inflater,container, false)

        binding.back.setOnClickListener {
           findNavController().popBackStack()
        }
        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_whatShouldDoFragment_to_supportResponseFragment)
        }

       return binding.root
    }

}