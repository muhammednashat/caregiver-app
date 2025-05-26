package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCofePurpose2Binding
import mnshat.dev.myproject.databinding.FragmentSupportCofeStep3Binding


class SupportCofeStep3Fragment : Fragment() {

    private lateinit var binding: FragmentSupportCofeStep3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSupportCofeStep3Binding.inflate(inflater, container, false)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_supportCofeStep3Fragment_to_whatShouldDoFragment)
        }

        return  binding.root
    }

}