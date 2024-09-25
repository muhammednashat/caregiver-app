package mnshat.dev.myproject.users.patient.tools.breath

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentMainBreathBinding
import mnshat.dev.myproject.factories.BreathViewModelFactory
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY


class MainBreathFragment : BasePatientFragment<FragmentMainBreathBinding>(){

    private lateinit var viewModel: BreathViewModel
    override fun initializeViews() {
        super.initializeViews()
        intiBackgroundBasedOnLang()
        binding.remainingTimeFormat.text = getString(R.string.remaining_time_format,0,"ثواني متبقية")
    }


    override fun getLayout()= R.layout.fragment_main_breath
    private fun intiBackgroundBasedOnLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }
    }



    override fun setupClickListener() {
        super.setupClickListener()


        binding.iconChooseDuration.setOnClickListener {

            ChooseDurationBreathFragment().show(
                childFragmentManager,
                ChooseDurationBreathFragment::class.java.name
            )

        }
        binding.icBack.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.finishExercise.setOnClickListener {
            viewModel.clearData()
            findNavController().popBackStack()
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

            val selectedDuration = viewModel.getSelectedDurationInMillis().toDouble()
            it?.let {
                binding.progressBar.progress =
                    it.toDouble().div(selectedDuration).times(100).toInt()
            }
        }

        viewModel.showDialog.observe(viewLifecycleOwner) {
         it?.let {
             if (it) {
                 showRepeatedExerciseDialog()
                 viewModel.restShowDialog()
             }
         }

        }

        viewModel.remainingTime.observe(viewLifecycleOwner){
            it?.let{
                binding.remainingTimeFormat.text =
                    getString(R.string.remaining_time_format, it, "ثواني متبقية")
                updateUiBaseCurrentPhase(it)
            }
        }

        viewModel.resetProgress.observe(viewLifecycleOwner) {
         it?.let {
            if (it){
                resetProgress()
                viewModel.resetRestProgress()

            }
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
            viewModel.resetIsTimerRunning()
            viewModel.onStartButtonClicked()
        }
    }

    private fun updateStartingButtonUi(it: Boolean) {
      val text = if (it)  getString(R.string.starting_over) else getString(R.string.click_to_start)
       val imageResource = if (it) R.drawable.ic_sync else R.drawable.ic_play
       binding.textView.text = text
       binding.imageView.setImageResource(imageResource)
    }

    private fun resetProgress(){
        binding.imageViewFace.setImageResource(R.drawable.image_face0)
        binding.progressBar.progress = 100
        binding.textInstructions.text = getString(R.string.click_on_the_box_below_to_get_started)
        viewModel.resetIsTimerRunning()

    }

    private fun updateUiBaseCurrentPhase(remainingTime: Int) {
        val listPhases = viewModel.gitDurationAsPhases()
        val phase = listPhases[3].plus(1)
        when(remainingTime){
            listPhases[0] -> {
                binding.imageViewFace.setImageResource(R.drawable.image_face1)
                binding.textInstructions.text = getString(R.string.inhale, phase)
            }
            listPhases[1] -> {
                binding.imageViewFace.setImageResource(R.drawable.image_face2)

                binding.textInstructions.text = getString(R.string.hold_air, phase)
            }
            listPhases[2] -> {
                binding.imageViewFace.setImageResource(R.drawable.image_face3)

                binding.textInstructions.text = getString(R.string.exhale_slowly, phase)
            }
            listPhases[3] -> {
                binding.imageViewFace.setImageResource(R.drawable.image_face0)

                binding.textInstructions.text = getString(R.string.take_rest, phase)
            }


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.progressBar.progress = 100
        viewModel.clearData()
    }





}