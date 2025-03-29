package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.view.Window
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
import mnshat.dev.myproject.databinding.StaionDescriptionDialogBinding
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
                } else {
                    setText( numberTask,task.enTitle , task.enDescription)
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
              binding.textTitle.text = title
          }else{
              binding.textTitle.text = title
          }
      }

           setDescriptionText()
      


    }

    private fun setDescriptionText() {
        val headerColor = "#204167"
//        binding.textDescription.text = Html.fromHtml(description )
        binding.textDescription.text = Html.fromHtml(
"<h2><font color='red'>What I sometimes feel:</font></h2>Sometimes I feel like I can't see anything in life except black and white... I feel a pain that exhausts my soul and my mind... I have the right to live happily... Feeling this sadness and pain means that there is something wrong with me, and I don't know what to do. <h2><font color='red'>What I need to know?</font></h2>It is absolutely okay to feel upset, sad or in pain. These are human feelings that do not shame the person who feels them, and they do not mean objection to the decrees of Allaah The Almighty or time to give up. It is okay to not be okay sometimes. These feelings are part of life, and if you are looking forward to your quest for perfection, you must accept that you are weak, because you were created imperfect and will never be complete, and there is no perfect person on this earth who is always happy. The key to healing is to cope with these feelings and not deny them.<br><br>\\\" + Others often suggest that we should suppress these feelings, saying things like 'Look at the troubles of others; your misfortune will seem easier’' and other words that make me feel that I am not satisfied with “God's will . Such words can be frustrating and may leave us feeling invalidated or misunderstood, as if our emotions or our right to feel them are not being acknowledged.  As the proverb says, ‘Whose hand is not under the stone does not hurt’, so  practise saying the proverb ‘Let it go in one ear and out the other’. It’s okay to acknowledge and accept these feelings. The proverb says, ‘Patience is a door to bitterness until it passes’, and this is the first step towards understanding yourself better, regaining your acceptance of life’s challenges and experiences to help face its circumstances. <h2><font color='red'>What to do?</font></h2><b>1. Accept and acknowledge your feelings:</b> It is important to allow yourself to accept your feelings without feeling guilty or ashamed.<br> <b>2. Talk about it with somebody you are comfortable talking with:</b> Whether you talk with a friend, family member, or counsellor, talking about your feelings can provide you with comfort. You do not have to go through this alone.<br> <b>3. Allow yourself to cry or rest:</b> Sometimes, emotions can be overwhelming, and that’s normal. Crying or taking time to recharge are important steps in healing.<br> <b>4. Practice self-care:</b> Participate in activities that help you relax and recharge your energy, whether by, for example, journalling or going for a walk. Self-care allows you to focus on your well-being.<br> <b>5. Seek professional support:</b> If grief or pain becomes overwhelming or persistent, a mental health professional can provide guidance, tools, and strategies to help you express that grief and to heal."        )

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
     fun showDescriptionDialog(image:Int,title:String,text:String) {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = StaionDescriptionDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)

        val window = dialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.8).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }

        dialogBinding.image.setImageResource(image)
        dialogBinding.title.text = title
        dialogBinding.text.text= text

        dialogBinding.icClose.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.button.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }



    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }


}