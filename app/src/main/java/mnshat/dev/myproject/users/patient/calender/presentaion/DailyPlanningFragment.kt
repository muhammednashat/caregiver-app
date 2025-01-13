package mnshat.dev.myproject.users.patient.calender.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentDailyPlanningBinding
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class DailyPlanningFragment : BaseFragment(),OnDayClickListener {

    private lateinit var  binding: FragmentDailyPlanningBinding
    private val viewModel: CalenderViewModel by viewModels()
    private lateinit var daysAdapter: DaysAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentDailyPlanningBinding.inflate(inflater, container, false)
        binding.calendarView.selectedDate = viewModel.today
        viewModel.getDays()
        setListeners()
        observing()
        return  binding.root
    }

    private fun setListeners() {
        binding.addButton.setOnClickListener{
            ChooseDayFragment().show(childFragmentManager, ChooseDayFragment::class.java.name)
        }
            binding.tracking.setOnClickListener {
                val day = binding.calendarView.selectedDate
                viewModel.setPickedDate(day)
                findNavController().navigate(R.id.action_dailyPlannerFragment_to_dayPlanFragment)
        }
        binding.back.setOnClickListener {
           activity?.finish()
        }
        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            viewModel.daysList.value?.let {

                isHideTrackingContainer(it,date)
//                if (it.contains(date)) {
//                    viewModel.setPickedDate(date)
//                    binding.trackingContainer.alpha = 1.0f
//                }else{
//                    binding.trackingContainer.alpha = 0.0f
//                }
            }
        }
    }
    private fun  isHideTrackingContainer(days: java.util.HashSet<CalendarDay>, day: CalendarDay){
        if (days.contains(day)) {
            viewModel.setPickedDate(day)
            binding.trackingContainer.alpha = 1.0f
            binding.noTasks.visibility = View.GONE
        }else{
            binding.trackingContainer.alpha = 0.0f
            binding.noTasks.visibility = View.VISIBLE
        }
    }

    private fun observing() {
        viewModel.daysList.observe(viewLifecycleOwner) { days ->
            days?.let {
                decorateViews(days)
                isHideTrackingContainer(days,viewModel.today)
            }
        }
        viewModel.taskList.observe(viewLifecycleOwner) { tasks ->
        }
    }


    private fun decorateViews(days: HashSet<CalendarDay>) {
        binding.calendarView.addDecorator(TaskDecorator(days))
    }

    override fun onDayClick(day: CalendarDay) {
        viewModel.setPickedDate(day)
        findNavController().navigate(R.id.action_dailyPlannerFragment_to_dayPlanFragment)

    }


}