package mnshat.dev.myproject.users.patient.calender

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentDailyPlannerBinding

@AndroidEntryPoint
class DailyPlannerFragment : BaseFragment() {

    private lateinit var  binding: FragmentDailyPlannerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentDailyPlannerBinding.inflate(inflater, container, false)
        return  binding.root

    }

}