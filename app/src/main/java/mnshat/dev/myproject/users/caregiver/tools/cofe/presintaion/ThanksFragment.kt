package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentSupportResponseBinding
import mnshat.dev.myproject.databinding.FragmentThanksBinding


class ThanksFragment : Fragment() {


    private lateinit var binding: FragmentThanksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentThanksBinding.inflate(inflater,container, false)


        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_supportResponseFragment_to_thanksFragment)
        }

        return binding.root
    }

}