package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentTrackingMoodBinding
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.DayMoodTracking
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.adapters.TrackingMoodAdapter
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.viewmodels.MoodTrackingViewModel

@AndroidEntryPoint
class TrackingMoodFragment : BaseFragment() {

    private val viewModel: MoodTrackingViewModel by activityViewModels()
    private lateinit var adapter: TrackingMoodAdapter
    private lateinit var binding: FragmentTrackingMoodBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = FragmentTrackingMoodBinding.inflate(inflater,container, false)
        showProgressDialog()
        observeViewModel()
        setUpListener()
        return  binding.root

    }

    private fun setUpListener() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.getDayMoodTracking().observe(viewLifecycleOwner) { dayMoodTrackingList ->
            if (dayMoodTrackingList?.size == 0){
                binding.noTracking.visibility = View.VISIBLE
            }else{
                setUpRecyclerView(dayMoodTrackingList)
            }
            dismissProgressDialog()
        }
    }

    private fun setUpRecyclerView(dayMoodTrackingList: List<DayMoodTracking>?) {
     adapter  = TrackingMoodAdapter(dayMoodTrackingList!!,viewModel.getEffectingMood(requireActivity()),viewModel.getEmojisStatus(requireContext()))

        binding.recyclerView.adapter = adapter
    }

}