package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentTrackingMoodBinding

@AndroidEntryPoint
class TrackingMoodFragment : BaseFragment() {

    private val viewModel: MoodViewModel by viewModels()
    private lateinit var binding: FragmentTrackingMoodBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = FragmentTrackingMoodBinding.inflate(inflater,container, false)
        observeViewModel()
        return  binding.root

    }

    private fun observeViewModel() {
        viewModel.getDayMoodTracking()
    }

}