package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentFriendMessageBinding

class FriendMessageFragment : Fragment() {

    private lateinit var binding: FragmentFriendMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFriendMessageBinding.inflate(inflater, container, false)
        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_friendMessageFragment_to_cupsMeaningFragment)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root

    }

}