package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentIntroCofe3Binding


class Intro3Fragment : Fragment() {


    private lateinit var binding: FragmentIntroCofe3Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentIntroCofe3Binding.inflate(inflater)

        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_intro3Fragment2_to_step1Fragment)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root

    }

}