package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
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

        checkInternetConnection()

        return  binding.root

    }


    private fun checkInternetConnection() {
        if (isConnected()) {
            showProgressDialog()
            viewModel.retrieveTracingListRemotely()
            observeViewModel()
            setUpListener()
        } else {
            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.no_internet_connection))
            .setMessage(getString(R.string.please_check_your_internet_connection_and_try_again))
            .setPositiveButton(getString(R.string.try_again)) { dialog, _ ->
                checkInternetConnection()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun setUpListener() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.trackingList.observe(viewLifecycleOwner){
            if (it?.size == 0){
                binding.noTracking.visibility = View.VISIBLE
            }else{
                setUpRecyclerView(it)
            }
            dismissProgressDialog()
        }


    }

    private fun setUpRecyclerView(dayMoodTrackingList: List<DayMoodTracking>?) {
        adapter  = TrackingMoodAdapter(dayMoodTrackingList!!,viewModel.getEffectingMood(requireActivity()),viewModel.getEmojisStatus(requireContext()))

        binding.recyclerView.adapter = adapter
    }




}