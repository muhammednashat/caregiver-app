package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCofeIntro2Binding


class CofeIntro2Fragment : Fragment() {

private lateinit var binding: FragmentCofeIntro2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCofeIntro2Binding.inflate(inflater)

        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_cofeIntro2Fragment_to_cofeIntro3Fragment)
        }
        binding.back.setOnClickListener {
           findNavController().popBackStack()
        }

        return binding.root

    }

}