package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import android.net.Uri
import android.text.Html
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.Task
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.RELIGION

@AndroidEntryPoint
open class BaseDailyProgramFragment : BaseFragment() {

     val viewModel: DayTaskViewModel by viewModels()
    private  var _fragment = ""
    lateinit var binding: LayoutTaskBinding
    lateinit var task: Task
    var player: ExoPlayer? = null
    var isSyncNeeded = false

    fun getTaskFromList(index: Int, numberTask: Int ,) {
        showProgressDialog()
        viewModel.listOfTasks.let { listOfTasks ->
             task = listOfTasks[index]
            task.let {
                val currentLang = viewModel.sharedPreferences.getString(LANGUAGE)
                if (currentLang != ENGLISH_KEY) {
                    setText( numberTask,task.arTitle , task.arDescription)
//
//                   if (_fragment == "BehaviouralActivationFragment"){
//                       binding.textTitle.text = getString(R.string.mission, numberTask, task.arTitle)
//                   }else{
//                       binding.textTitle.text = task.arTitle
//                   }
//                    binding.textDescription.text = Html.fromHtml(task.arDescription)

                } else {
                    setText( numberTask,task.enTitle , task.enDescription)

//                    if (_fragment == "BehaviouralActivationFragment"){
//                        binding.textTitle.text = getString(R.string.mission, numberTask, task.enTitle)
//                    }else{
//                        binding.textTitle.text = task.enTitle
//                    }
//                    binding.textDescription.text = Html.fromHtml(task.enDescription)
                }
                checkType(task.type)
            }
        }
    }

    private fun setText ( numberTask: Int ,title: String?, description: String?){
      if (title?.isEmpty() == true){
          binding.textTitle.visibility = View.GONE
      }else{
          if (_fragment == "BehaviouralActivationFragment"){
              binding.textTitle.text = getString(R.string.mission, numberTask, title)
          }else{
              binding.textTitle.text = title
          }
      }
        binding.textDescription.text = Html.fromHtml(description)
    }

    fun changeColorOfTaskImage(status: Int?, root: ConstraintLayout, image: ImageView){
        when(status){
            1 -> {
                image.setImageResource( R.drawable.ic_check_blue)
                root.setBackgroundResource(R.drawable.circle_blue_dark)
            }
            2 -> {
                val params = root.layoutParams
                params.width = 70
                params.height = 70
                root.layoutParams = params
                root.setBackgroundResource(R.drawable.circle_orange_with_border)
            }
        }
    }

    fun hideSpiritualIcon(constraintTask: ConstraintLayout, line: View) {
        if( !viewModel.sharedPreferences.getBoolean(RELIGION)){
            constraintTask.visibility = View.GONE
            line.visibility = View.GONE
        }
    }
    fun witchFragment(fragment: String) {
        _fragment = fragment
    }
    fun getNextTask(index: Int, numberTask: Int):Int {
         player?.pause()
      return  if (viewModel.listOfTasks != null) {
            val currentTaskIndex = (index + 1) % viewModel.listOfTasks.size
            getTaskFromList(currentTaskIndex,numberTask)
          currentTaskIndex
        }else 0
    }
    fun updateCompletionRate() {
        viewModel.status.remaining = viewModel.status.remaining?.minus(1)
        if (viewModel.sharedPreferences.getBoolean(RELIGION)) {
            viewModel.status.completionRate = viewModel.status.completionRate?.plus(30)
        } else {
            viewModel.status.completionRate = viewModel.status.completionRate?.plus(50)
        }
        viewModel.updateCurrentTaskLocally()

    }

    private fun checkType(type: Int?) {
        when (type) {
            1 -> {
                displayImage(type)
            }

            2 -> {
                hideViews(type)
                playVideoAudio(Uri.parse(task.link))
            }

            3 -> {
                hideViews(type)
                playVideoAudio(Uri.parse(task.link))
            }

            4 -> {
                hideViews(type)
                showText(type)
            }
        }
    }
    private fun playVideoAudio(uri: Uri) {
        player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
            binding.exoPlayer.player = exoPlayer
            val mediaItem = MediaItem.fromUri(uri)
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
        Glide.with(this).load(task.image).into(binding.imageView)
        hideViews(int)
        dismissProgressDialog()
    }

    private fun hideViews(int: Int) {

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