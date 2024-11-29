package mnshat.dev.myproject.users.patient.calender.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentDayPlanBinding
import mnshat.dev.myproject.users.patient.calender.domain.entity.CalenderDayTask

@AndroidEntryPoint
class DayPlanFragment : BaseFragment() {

    private lateinit var binding: FragmentDayPlanBinding
    private lateinit var adapter: DayPlanAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDayPlanBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        return  binding.root
    }

    private fun setUpRecyclerView() {
        val list = listOf(CalenderDayTask(1, "title", true,"")
            ,CalenderDayTask(1, "title", true,"")
            ,CalenderDayTask(1, "title", true,"")
            ,CalenderDayTask(1, "title", true,"")
            ,CalenderDayTask(1, "title", true,"")
            ,CalenderDayTask(1, "title", true,"")
            ,CalenderDayTask(1, "title", true,"")
            ,CalenderDayTask(1, "title", true,"")
            ,CalenderDayTask(1, "title", true,"")
            ,)
        adapter = DayPlanAdapter(list)
        binding.recyclerView.adapter = adapter

    }

}