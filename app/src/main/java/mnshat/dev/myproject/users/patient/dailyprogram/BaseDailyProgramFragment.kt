package mnshat.dev.myproject.users.patient.dailyprogram

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.model.Task
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY

abstract class BaseDailyProgramFragment<T : ViewDataBinding> : BasePatientFragment<T>() {

    lateinit var viewModel: DailyProgramViewModel
    lateinit var task: Task
    var player: ExoPlayer? = null
    var isSyncNeeded = false



    fun getTaskFromList(index: Int, numberTask: Int) {
        Log.e("TAG" , "12")
        val binding = binding as LayoutTaskBinding
        showProgressDialog()
        viewModel.listOfTasks.let { listOfTasks ->
             task = listOfTasks[index]
            task.let {
                if (currentLang != ENGLISH_KEY) {
                    binding.textTitle.text = getString(R.string.mission, numberTask, task.arTitle)
                    binding.textDescription.text = task.arDescription
                } else {
                    binding.textTitle.text = getString(R.string.mission, numberTask, task.enTitle)
                    binding.textDescription.text = task.enDescription
                }
                Log.e("TAG" , "12322")
                checkType(task.type)
            }
            Log.e("TAG" , "1232")

        }
        Log.e("TAG" , "312")

    }

    fun getNextTask(index: Int, numberTask: Int):Int {
         player?.pause()
      return  if (viewModel.listOfTasks != null) {
            val currentTaskIndex = (index + 1) % viewModel.listOfTasks.size
            getTaskFromList(currentTaskIndex,numberTask)
          currentTaskIndex
        }else 0
    }

    fun showCompletionTaskDialog(action: Int) {
        findNavController().navigate(action)
    }

    private fun checkType(type: Int?) {
        when (type) {
            1 -> {
                displayImage(type)
            }

            2 -> {
                hideViews(type)
                playVideoAudio()
            }

            3 -> {
                hideViews(type)
                playVideoAudio()
            }

            4 -> {
                hideViews(type)
                showText(type)
            }
        }
    }
    private fun playVideoAudio() {
        val binding = binding as LayoutTaskBinding
        player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
            binding.exoPlayer.player = exoPlayer
            val mediaItem = MediaItem.fromUri(Uri.parse(task.link))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            dismissProgressDialog()
//            exoPlayer.playWhenReady = true
        }

    }

    private fun showText(int: Int) {
        hideViews(int)
    }

    private fun displayImage(int: Int) {
        val binding = binding as LayoutTaskBinding
        Glide.with(this).load(task.image).into(binding.imageView)
        hideViews(int)
        dismissProgressDialog()
    }

    private fun hideViews(int: Int) {
        val binding = binding as LayoutTaskBinding

        when (int) {
            //image
            1 -> {
                binding.exoPlayer.visibility = View.GONE
                binding.imageView.visibility = View.VISIBLE

            }
            //video
            2 -> {
                binding.imageView.visibility = View.GONE
                binding.exoPlayer.visibility = View.VISIBLE
            }
            //audio
            3 -> {
                binding.imageView.visibility = View.GONE
                binding.exoPlayer.visibility = View.VISIBLE
            }

        }
    }



    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }


}