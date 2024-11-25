package mnshat.dev.myproject.users.patient.calender.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentChooseDayBinding

@AndroidEntryPoint
class ChooseDayFragment : BaseBottomSheetDialogFragment() {

private lateinit var  binding: FragmentChooseDayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseDayBinding.inflate(inflater, container, false)
         setListeners()
        return  binding.root
    }

    private fun setListeners() {
        binding.startButton.setOnClickListener{
            ChooseActivitiesFragment().show(childFragmentManager, ChooseActivitiesFragment::class.java.name)
        }
    }
}