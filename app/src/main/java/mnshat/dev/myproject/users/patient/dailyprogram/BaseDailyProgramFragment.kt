package mnshat.dev.myproject.users.patient.dailyprogram

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.CurrentTask
import mnshat.dev.myproject.model.StatusDailyProgram
import mnshat.dev.myproject.model.Task
import mnshat.dev.myproject.users.patient.main.BaseUserFragment
import mnshat.dev.myproject.util.CURRENT_TASK
import mnshat.dev.myproject.util.DAY_TASK
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.STATUS
import mnshat.dev.myproject.util.log

open abstract class BaseDailyProgramFragment<T : ViewDataBinding> : BaseUserFragment<T>() {

    lateinit var _viewModel: SharedDailyProgramViewModel

    lateinit var task: Task
    lateinit var _currentTask: CurrentTask
    lateinit var status: StatusDailyProgram
    lateinit var listOfTasks: List<Task>
    var player: ExoPlayer? = null
    var isSyncNeeded = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        _viewModel = ViewModelProvider(requireActivity())[SharedDailyProgramViewModel::class.java]
        super.onActivityCreated(savedInstanceState)
    }

    fun getTaskFromList(index: Int, numberTask: Int) {
        val binding = binding as LayoutTaskBinding
        showProgressDialog()
        task = listOfTasks.get(index)
        task.let {
            if (currentLang != ENGLISH_KEY) {
                binding.textTitle.text = getString(R.string.mission, numberTask, task.arTitle)
                binding.textDescription.text = task.arDescription
            } else {
                binding.textTitle.text = getString(R.string.mission, 1, task.enTitle)
                binding.textDescription.text = task.enDescription
            }
            val type = task.type
            checkType(type)
        }

    }
    fun getNextTask(index: Int, numberTask: Int):Int {
         player?.pause()

      return  if (listOfTasks.isNotEmpty()) {
            val currentTaskIndex = (index + 1) % listOfTasks.size
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

    fun changeColo3r() {
        val binding = binding as LayoutTaskBinding
//        changeColorOfTaskImage(status.contemplation, binding.task1)
//        changeColorOfTaskImage(status.activity, binding.task2)
//        changeColorOfTaskImage(status.behaviorOrSpiritual, binding.task3)
//        changeColorOfTaskImage(status.gratitude, binding.task4)
    }

    fun updateCurrentTaskLocally() {
        Log.e("TAG", "updateCurrentTaskLocally")
        sharedPreferences.storeObject(CURRENT_TASK, _currentTask)
        isSyncNeeded = true
    }

    fun updateCurrentTaskRemotely() {
        Log.e("TAG", "updateCurrentTaskRemotely")
        val map = mapOf(DAY_TASK to _currentTask.dayTask!!, STATUS to _currentTask.status!!)
        FirebaseService.updateTasksUser(FirebaseService.userId, map) {
            if (it) {
                Log.e("TAG", " updateCurrentTaskRemotely true")
            } else {
                Log.e("TAG", " updateCurrentTaskRemotely false")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
        if (isSyncNeeded){
            log("isSyncNeeded")
            updateCurrentTaskRemotely()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }


}