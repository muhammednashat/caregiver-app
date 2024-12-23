package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentPostDailyProgramBinding


class PostDailyProgramFragment : BaseFragment() {
private lateinit var binding:FragmentPostDailyProgramBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostDailyProgramBinding.inflate(inflater,container,false)

        return binding.root
    }

}