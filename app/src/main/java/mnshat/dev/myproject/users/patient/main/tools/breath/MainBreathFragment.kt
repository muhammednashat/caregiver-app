package mnshat.dev.myproject.users.patient.main.tools.breath

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentMainBreathBinding
import mnshat.dev.myproject.factories.BreathViewModelFactory
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.log


class MainBreathFragment : BasePatientFragment<FragmentMainBreathBinding>(){

    private lateinit var viewModel: BreathViewModel
    override fun initializeViews() {
        super.initializeViews()
        binding.remainingTimeFormat.text = getString(R.string.remaining_time_format,0,"ثواني متبقية")

    }


    override fun getLayout()= R.layout.fragment_main_breath


    override fun setupClickListener() {
        super.setupClickListener()


        binding.iconChooseDuration.setOnClickListener {

            ChooseDurationBreathFragment().show(
                childFragmentManager,
                ChooseDurationBreathFragment::class.java.name
            )

        }



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        viewModel.setCurrentDuration(0)
        observeViewModel()

    }

    private fun initViewModel() {
        val factory = BreathViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[BreathViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
    private fun observeViewModel(){



        viewModel.progressState.observe(viewLifecycleOwner) {


            binding.progressBar.progress = it.toDouble().div(t.toDouble()).times(100).toInt()

        }
        viewModel.showDialog.observe(viewLifecycleOwner) {
         it?.let {
             showRepeatedExerciseDialog()
         }

        }

        viewModel.isTimerRunning.observe(viewLifecycleOwner) {

            it?.let {
                updateStartingButtonUi(it)
            }
        }
    }

    private fun showRepeatedExerciseDialog() {
        showTemporallyDialog(
            getString(R.string.would_you_like_to_repeat_the_exercise_again),
            getString(R.string.this_procedure_will_delete_the_current_exercise_data_and_replace_it_with_the_new_exercise_data),
            R.drawable.ic_refresh,
            getString(R.string.starting_over)
        ) {
            requireActivity().finish()
        }
    }

    private fun updateStartingButtonUi(it: Boolean) {
      val text = if (it)  getString(R.string.starting_over) else getString(R.string.click_to_start)
       val imageResource = if (it) R.drawable.ic_sync else R.drawable.ic_play
       binding.textView.text = text
       binding.imageView.setImageResource(imageResource)
    }




}