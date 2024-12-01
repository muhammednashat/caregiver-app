package mnshat.dev.myproject.users.patient.calender.presentaion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentDailyPlanningBinding

@AndroidEntryPoint
class DailyPlanningFragment : BaseFragment() {

    private lateinit var  binding: FragmentDailyPlanningBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentDailyPlanningBinding.inflate(inflater, container, false)
        val hashSet = HashSet<CalendarDay>()
        hashSet.add(CalendarDay.from(2024, 11, 22))
        hashSet.add(CalendarDay.from(2024, 10, 13))
        hashSet.add(CalendarDay.from(2024, 9, 29))
        hashSet.add(CalendarDay.from(2024, 12, 18))
        val tast = TaskDecorator(hashSet)
        binding.calendarView.addDecorator(tast)

//        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
//        Log.e("TAG","" +date)
//            hashSet.add(date)
//
//        }


        setListeners()
        return  binding.root

    }

    private fun setListeners() {
    binding.addButton.setOnClickListener{
        ChooseDayFragment().show(childFragmentManager, ChooseDayFragment::class.java.name)
    }
        binding.tracking.setOnClickListener {
            findNavController().navigate(R.id.action_dailyPlannerFragment_to_dayPlanFragment)
    }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

    }


}