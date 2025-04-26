package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentStep2Binding
import mnshat.dev.myproject.databinding.FragmentStep6Binding


class Step6Fragment : Fragment() {


    private lateinit var binding: FragmentStep6Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStep6Binding.inflate(inflater)

        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_step6Fragment_to_exitCofeFragment)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root

    }

}