package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentStep2Binding
import mnshat.dev.myproject.databinding.FragmentStep4Binding


class Step4Fragment : Fragment() {


    private lateinit var binding: FragmentStep4Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStep4Binding.inflate(inflater)

        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_step4Fragment_to_step5Fragment)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root

    }

}