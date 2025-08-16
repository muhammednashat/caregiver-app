package mnshat.dev.myproject.users.patient.tools.breathing.presntaion

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentMainBreathBinding
import androidx.fragment.app.activityViewModels
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class MainBreathFragment : BaseFragment() {


    private  val viewModel : BreathViewModel by activityViewModels()
    private  lateinit var  binding : FragmentMainBreathBinding

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBreathBinding.inflate(inflater, container, false)

        initializeViews()
        setupClickListener()
        observeViewModel()
        return binding.root
    }

     fun initializeViews() {

         if (viewModel.soundId == 0) {
             binding.sound.setImageResource(R.drawable.no_musiz)
         }
        binding.remainingTimeFormat.text =
            getString(R.string.remaining_time_format, 0, "ثواني متبقية")
    }


     fun setupClickListener() {

        binding.chooseDuration.setOnClickListener {
            ChoosingDurationDialog().show(childFragmentManager, "ChoosingDurationDialog")
        }

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.sound.setOnClickListener {
            ChoosingSoundDialog().show(childFragmentManager, "ChoosingSoundDialog")
        }

      binding.startingButton.setOnClickListener {
          viewModel.onStartButtonClicked()
        }

        binding.finishExercise.setOnClickListener {
            viewModel.clearData()
            stopTickSound()
            findNavController().popBackStack()
        }

    }


    private fun observeViewModel() {

        viewModel.textDuration.observe(viewLifecycleOwner) {
            binding.textDuration.text = it
        }

        viewModel.progressState.observe(viewLifecycleOwner) {
            val selectedDuration = viewModel.getSelectedDurationInMillis().toDouble()
            it?.let {
                binding.progressBar.progress =
                    it.toDouble().div(selectedDuration).times(100).toInt()
            }
        }
        viewModel.changeSound.observe(viewLifecycleOwner) {

            if (viewModel.soundId == 0){
                binding.sound.setImageResource(R.drawable.no_musiz)

            }else{
                binding.sound.setImageResource(R.drawable.musiz)
            }
                playTickSound()
        }



        viewModel.showDialog.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    showRepeatedExerciseDialog()
                    viewModel.restShowDialog()
                }
            }

        }

        viewModel.remainingTime.observe(viewLifecycleOwner) {
            it?.let {
                if (it == 59) {
                    log("continue $it")
                    playTickSound()
                } else if(it == 0){
                    log("stop $it")
                    stopTickSound()
                }
                binding.remainingTimeFormat.text =
                    getString(R.string.remaining_time_format, it, "ثواني متبقية")
                updateUiBaseCurrentPhase(it)
            }
        }

        viewModel.resetProgress.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
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
        val text = if (it) getString(R.string.starting_over) else getString(R.string.click_to_start)
        val imageResource = if (it) R.drawable.ic_sync else R.drawable.ic_play
        binding.textView.text = text
        binding.imageView.setImageResource(imageResource)
    }

    private fun resetProgress() {
        binding.imageViewFace.setImageResource(R.drawable.image_face0)
        binding.progressBar.progress = 100
        binding.textInstructions.text = getString(R.string.click_on_the_box_below_to_get_started)
        viewModel.resetIsTimerRunning()

    }

    private fun updateUiBaseCurrentPhase(remainingTime: Int) {
        val listPhases = viewModel.gitDurationAsPhases()
        val phase = listPhases[3].plus(1)
        when (remainingTime) {
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
        mediaPlayer?.release()
        mediaPlayer = null
        binding.progressBar.progress = 100
        viewModel.clearData()
    }

    private fun playTickSound() {
        if(viewModel.soundId == 0){
            stopTickSound()
            return
        }
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, viewModel.soundId)
        mediaPlayer?.start()
    }

    private fun stopTickSound() {
        log("stopTickSound")
        mediaPlayer?.release()
        mediaPlayer = null
    }


}