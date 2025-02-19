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
        binding.textDescription.text = Html.fromHtml(
"كانت حنان تستعد لاجتماع عائلي كبير في منزلها. واستضافتها الدائمة لمثل هذه المناسبات أمرًا مهمًا بالنسبة لها، لكن ذلك يجعلها تشعر بالتوتر من أجل أن يكون كل شيء مثاليًا. عندما انتهت من الطهي، أدركت أن الطبق الرئيسي قد احترق قليلاً، ففزع قلبها بأن هذه كارثة، وفكرت، سيعتقد الجميع أنني لا أستطيع حتى الطهي بشكل صحيح<br>ظل هذا الفكر معها وهي تسارع الوقت لإنهاء الاستعدادات. بدأت تلاحظ كل شيء فلم يكن المنزل نظيفًا بما فيه الكفاية، ولم تكن طاولة الطعام مرتبة بما فيه الكفاية، وقد يعتقد الضيوف أنها غير منظمة. تراكمت كل الأفكار؛ مما جعلها تشعر بقلق أكبر<br>بحلول الوقت الذي وصلت فيه الأسرة، لم تتمكن حنان من التخلص من توترها. كانت تتحرك ببطء خلال العشاء، مقتنعة أن الجميع يحكمون عليها بصمت. وعندما أثنى أحدهم على الطعام، لم تسمع في ذهنها سوى إنهم مهذبون فقط. طوال المساء، شعرت وكأنها فشلت، على الرغم من أن عائلتها قضت وقتًا رائعًا.<br><h2>ولكن ما الذي أوصل حنان إلى الشعور بالفشل وعكر صفو يومها، وحرمها من الاستمتاع بوقتها مع عائلتها؟</h2>إنها الأفكار. تتُولَد الأفكار في العقل في كل موقف، وغالبًا ما تكون المشاعر سواء كانت سلبية أم إيجابية مرتبطة بهذه الأفكار. ورغم أن الأفكار قد تؤثر على المشاعر، إلا أن العلاقة يمكن أن تسير أيضًا في الاتجاه الآخر<br><br>• “انا فاشل.”<br>• “لا شيء يسير على ما يرام بالنسبة لي.”<br>• “لن أتحسن أبدًا.”<br>قد تبدو هذه الأفكار صغيرة، لكنها مع الوقت تتراكم بسرعة. فعندما تبدأ في الاعتقاد بأني لن أتحسن أبدا ويدور في ذهنك كما في المثل إجا ليكحلها عماها، فذلك يمنعك من تجربة أشياء جديدة أو الاعتناء بنفسك. بمرور الوقت لا تبق هذه الأفكار في ذهنك فحسب، بل تؤثر على مزاجك وسلوكك وكيفية تفاعلك مع الآخرين؛ و يجعل شعورك أسوأ مما كان.<br><h2>What you need to know?</h2>Your thoughts have a huge impact on how you feel and behave. An enduring pattern of profoundly negative thoughts about yourself, your life, or both over time can lead to depression. Just because you think or believe something doesn’t mean it’s true.<br><h2>What to do?</h2><b>1. Recognize the pattern:</b><br>Notice when your negative thoughts recur, and what triggers, situations, and times these thoughts occur and escalate. For example, if every time your boss comments, you think, “Nothing is going right for me”, then this thought can lead to other thoughts such as “I always mess things up” and “I’m not good at anything”. Try to step back and remember the proverb, “Shut thedoor from which the wind comes and relax”. Ask yourself, “Is everything really going wrong, or is my present mood or my mind making it seem that way?”<br><br><b>2. Challenge your negative thoughts:</b><br>The next time you find yourself thinking thoughts like , “Nothing is going right for me” stop yourself and ask, “Is that really true?” Consider the evidence for this belief, and the evidence that it is not true.<br><br><b>3. Reframe negative thoughts with healthier thoughts: </b><br>After considering the evidence for the belief “Nothing is going right for me” try reframing it with something more optimistic: “Some things are not going right for me, but it doesn’t mean that nothing is going right.”<br>You may find that you’re being harsher on yourself than you are on someone else. You can try this with a more challenging belief, such as “I always mess things up” or “I’m not good at anything”. You can consider the evidence for and against such thoughts, and consider an alternative belief: “I’ve had failures, but that doesn’t mean I’m a failure”.<br><br><b>Remember:</b><br>Your thoughts can help to shape your feelings, but you can shape your thoughts.Learning to challenge and change negative thought patterns is not easy, but it is a powerful way to improve your mood and behaviour. The next time you feel stuck in a vicious cycle of negativity, take a moment to stop, reflect, and reframe your thoughts.<br>"

//"Hanan was preparing for a big family gathering at her home. Hosting such events was always important to her, but it also made her feel stressed about making sure everything was perfect. When she finished cooking, she realized that the main dish was slightly burnt. “This is a disaster,” she thought, “Everyone will think I can’t even cook properly.This thought stayed with her as she raced to finish the preparations. She started noticing everything: the house wasn’t clean enough, the dining table wasn’t arranged enough, and the guests might think she was disorganized. All the thoughts piled up, making her feel even more anxious.<br>By the time the family arrived, Hanan couldn’t shake her stress. She moved slowly through dinner, convinced that everyone was silently judging her. When someone complimented the food, all she could hear in her mind was “They’re just being polite”. Throughout the evening, she felt like she had failed, even though her family had had a great time.<br><h2>But what made Hanan feel like a failure, spoil her day, and deprive her of enjoying her time with her family?!</h2>It’s thoughts… Thoughts are generated in the mind in every situation, and moods, whether negative or positive, are often associated with these thoughts. While thoughts can influence emotions, the connection can also work in the opposite direction.Like Hanan, we can allow negative thoughts to shape our entire day. Have you ever had thoughts such as these?:<br>• “I’m a failure.”<br>• “Nothing is going right for me.”<br>• “I’ll never get better.”<br>These thoughts may seem small, but over time they quickly add up. When you start to believe that “I’ll never get better” as the proverb says, “Instead of applying kohl to her eyes, he blinded her”, this will prevent you from trying new things or taking care of yourself. Over time, these thoughts don’t just stick with you—they affect your mood, your behaviour, and how you interact with others, making you feel worse.<br><h2>What you need to know?</h2>Your thoughts have a huge impact on how you feel and behave. An enduring pattern of profoundly negative thoughts about yourself, your life, or both over time can lead to depression. Just because you think or believe something doesn’t mean it’s true.<br><h2>What to do?</h2><b>1. Recognize the pattern:</b><br>Notice when your negative thoughts recur, and what triggers, situations, and times these thoughts occur and escalate. For example, if every time your boss comments, you think, “Nothing is going right for me”, then this thought can lead to other thoughts such as “I always mess things up” and “I’m not good at anything”. Try to step back and remember the proverb, “Shut thedoor from which the wind comes and relax”. Ask yourself, “Is everything really going wrong, or is my present mood or my mind making it seem that way?”<br><br><b>2. Challenge your negative thoughts:</b><br>The next time you find yourself thinking thoughts like , “Nothing is going right for me” stop yourself and ask, “Is that really true?” Consider the evidence for this belief, and the evidence that it is not true.<br><br><b>3. Reframe negative thoughts with healthier thoughts: </b><br>After considering the evidence for the belief “Nothing is going right for me” try reframing it with something more optimistic: “Some things are not going right for me, but it doesn’t mean that nothing is going right.”<br>You may find that you’re being harsher on yourself than you are on someone else. You can try this with a more challenging belief, such as “I always mess things up” or “I’m not good at anything”. You can consider the evidence for and against such thoughts, and consider an alternative belief: “I’ve had failures, but that doesn’t mean I’m a failure”.<br><br><b>Remember:</b><br>Your thoughts can help to shape your feelings, but you can shape your thoughts.Learning to challenge and change negative thought patterns is not easy, but it is a powerful way to improve your mood and behaviour. The next time you feel stuck in a vicious cycle of negativity, take a moment to stop, reflect, and reframe your thoughts.<br>"
        )
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