package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentIntroCofe2Binding


class Intro2Fragment : Fragment() {
    private lateinit var binding: FragmentIntroCofe2Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentIntroCofe2Binding.inflate(inflater)

        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_intro2Fragment2_to_intro3Fragment2)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root

    }

}