package mnshat.dev.myproject.users.patient.calender.presentaion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentChooseDayBinding

@AndroidEntryPoint
class ChooseDayFragment : BaseBottomSheetDialogFragment() {

    private lateinit var  binding: FragmentChooseDayBinding

    private val viewModel: CalenderViewModel by viewModels()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentChooseDayBinding.inflate(inflater, container, false)
            setUpCalenderView()
            setListeners()
            return  binding.root
        }

    private fun setUpCalenderView() {
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            binding.startButton.alpha = 1.0f
            viewModel.setPickedDate(date)
        }
    }

    private fun setListeners() {
            binding.startButton.setOnClickListener{
                 dismiss()
                requireActivity().findNavController(R.id.nav_host_auth)
                    .navigate(R.id.action_dailyPlannerFragment_to_chooseActivitiesFragment)
            }
        }
}