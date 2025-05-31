package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCupsMeaningBinding
import mnshat.dev.myproject.databinding.FragmentSupportCofeStep2Binding
import mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion.CofeViewModel
import mnshat.dev.myproject.util.log


class SupportCofeStep2Fragment : Fragment() {

    private  lateinit var binding: FragmentSupportCofeStep2Binding
    private val viewModel: CofeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentSupportCofeStep2Binding.inflate(inflater, container, false)
        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_supportCofeStep2Fragment_to_supportCofeStep3Fragment)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root

    }

}