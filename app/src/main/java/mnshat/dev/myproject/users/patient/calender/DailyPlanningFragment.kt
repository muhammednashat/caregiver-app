package mnshat.dev.myproject.users.patient.calender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.auth.AgeFragment
import mnshat.dev.myproject.databinding.FragmentDailyPlanningBinding

@AndroidEntryPoint
class DailyPlanningFragment : BaseFragment() {

    private lateinit var  binding: FragmentDailyPlanningBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentDailyPlanningBinding.inflate(inflater, container, false)
        setListeners()
        return  binding.root

    }

    private fun setListeners() {
    binding.addButton.setOnClickListener{
        ChooseDayFragment().show(childFragmentManager, ChooseDayFragment::class.java.name)
    }
    }


}