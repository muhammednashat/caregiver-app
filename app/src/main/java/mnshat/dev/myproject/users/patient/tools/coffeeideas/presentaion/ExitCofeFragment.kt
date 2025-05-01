package mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentExitCofeBinding
import mnshat.dev.myproject.users.patient.calender.presentaion.CalenderActivity


class ExitCofeFragment : Fragment() {

    private lateinit var binding: FragmentExitCofeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentExitCofeBinding.inflate(inflater)

        binding.exit.setOnClickListener {
            activity?.finish()

        }

        return  binding.root

    }

}