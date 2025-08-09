package mnshat.dev.myproject.users.patient.tools.breath.presntaion

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.DurationAdapter
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment2
import mnshat.dev.myproject.databinding.FragmentChooseDurationBreathBinding
import mnshat.dev.myproject.factories.BreathViewModelFactory
import mnshat.dev.myproject.model.Duration
import mnshat.dev.myproject.util.ENGLISH_KEY


class ChooseDurationBreathFragment :
    BaseBottomSheetDialogFragment2<FragmentChooseDurationBreathBinding>(){
    private lateinit var viewModel: BreathViewModel
    private lateinit var adapter: DurationAdapter


    override fun getLayout() = R.layout.fragment_choose_duration_breath


    override fun setupClickListener() {
        binding.close.setOnClickListener {
            dismiss()
        }

        binding.buttonConfirm.setOnClickListener {

            viewModel.setCurrentDuration(adapter.getSelectedPosition())
            viewModel.cancelCountdown()
            viewModel.resetProgress()
            viewModel.resetRemainingTime()
            dismiss()

        }
    }

    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        setUpRecyclerview(viewModel.getListOfDurations(),viewModel.getSelectedPosition())

    }

    private fun setUpRecyclerview(listOfDurations: List<Duration>, selectedPosition: Int) {
         adapter = DurationAdapter(listOfDurations, selectedPosition)
        binding.recyclerViewDurations.adapter= adapter
    }

    private fun initViewModel() {
        val factory = BreathViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[BreathViewModel::class.java]
        binding.lifecycleOwner = this
    }




}
