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
import mnshat.dev.myproject.util.TextToSpeechUtil

@AndroidEntryPoint
open class BaseDailyProgramFragment : BaseFragment() {

     val viewModel: DayTaskViewModel by viewModels()
     lateinit var textToSpeech: TextToSpeechUtil
     lateinit var htmlText: String

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

           setDescriptionText(description)
      


    }

    private fun setDescriptionText(description: String?) {
        val headerColor = "#204167"
        htmlText =
  "<h2><font color=$headerColor>ما أشعر به أحيانًا :</font></h2> أشعر أحيانًا أني لا أرى من ألوان الحياة إلا الأبيض والأسود. أشعر بألم ينهك روحي وتفكيري. إنه يحق لي أن أعيش بسعادة، فشعوري بهذا الحزن والألم يعني أن هناك خطأ ما فيّ، ولا أعرف ماذا أفعل. <h2><font color=$headerColor>ما أحتاج إلى معرفته؟</font>   </h2> لا بأس على الإطلاق أن تشعر بالضيق أو الحزن أو الألم. فهذه مشاعر ذات طابع إنساني لا تعيب صاحبها، ولا تعني اعتراضا على قدر أو يأسا من الحياة. إذ لا بأس ألا تكون بخير في بعض الأحيان، وهذ المشاعر هي جزء من الحياة، ومهما تتطلع بسعيك نحو الكمال لا بد أن تتقبل كونك ضعيفا، فإنك خلقت ناقصا ولن تكتمل أبدا، ولا يوجد على هذه الأرض إنسان كامل سعيد دائما؛ فالمفتاح للشفاء هو أن تتكيف مع تلك المشاعر وألا تنكرها.<br><br> غالبًا ما يخبرنا الآخرين أنه يجب علينا قمع هذه المشاعر، فنسمع منهم كلمات محبطة مثل  شوف بلاوي الناس تهون مصيبتك  وغيرها من الكلمات، والتي يشعرونني فيها بأني غير راضٍ بقضاء الله وحالي معهم كما يقول المثل (اللي ايده مو تحت الحجر ما بتوجعوا)، ولكن واجبك معهم أن تعمل على قول المثل (إذن من طين وإذن من عجين). لا بأس أن تعترف بهذه المشاعر وتتقبلها.  يقول المثل (دواء الدهر الصبر عليه) وأيضا (الصبر باب للمر حتى يمر) وأيضا (إذا غلي اللحم الصبر رخيص) وتلك هي خطوة أولى نحو فهم نفسك بشكل أفضل؛ لاستعادة فهمك للحياة واكتساب خبرات أقوى لمواجهة ظروفها. <h2><font color=$headerColor>ما يجب القيام به:</font></h2> <b><font color = $headerColor> ١. تقبل واعترف بمشاعرك:</font></b> من المهم أن تسمح لنفسك بتقبل مشاعرك دون الشعور بالذنب أو الخجل.<br> <b><font color = $headerColor> ٢. تحدث عن الأمر:</font></b> سواء مع صديق أو أحد أفراد الأسرة أو مستشار، فإن التحدث عن مشاعرك يمكن أن يوفر لك الراحة. لست مضطرًا إلى المرور بهذا بمفردك.<br> <b><font color = $headerColor> ٣.اسمح لنفسك بالبكاء أو الراحة:</font></b> في بعض الأحيان، قد تجتاحك المشاعر وهذا أمر طبيعي، فالبكاء أو أخذ الوقت لإعادة شحن طاقتك هي مراحل مهمة في الشفاء.<br> <b><font color = $headerColor> ٤.مارس الرعاية الذاتية:</font></b> شارك في الأنشطة التي تساعدك على الاسترخاء وإعادة شحن طاقتك، سواء كانت كتابة مذكرات أو الذهاب في نزهة؛ فالرعاية الذاتية تتيح لك التركيز على رفاهيتك.<br> <b><font color = $headerColor> ٥.طلب الدعم المهني:</font></b> إذا اجتاحك أو استمر معك الحزن أو الألم، فيمكن لمتخصص الصحة النفسية تقديم التوجيه، والأدوات والاستراتيجيات؛ لمساعدتك على التشافي.<br>"

//        binding.textDescription.text = Html.fromHtml(description )

        binding.textDescription.text = Html.fromHtml(

            "<h2><font color=$headerColor>ما أشعر به أحيانًا :</font></h2> أشعر أحيانًا أني لا أرى من ألوان الحياة إلا الأبيض والأسود. أشعر بألم ينهك روحي وتفكيري. إنه يحق لي أن أعيش بسعادة، فشعوري بهذا الحزن والألم يعني أن هناك خطأ ما فيّ، ولا أعرف ماذا أفعل. <h2><font color=$headerColor>ما أحتاج إلى معرفته؟</font>   </h2> لا بأس على الإطلاق أن تشعر بالضيق أو الحزن أو الألم. فهذه مشاعر ذات طابع إنساني لا تعيب صاحبها، ولا تعني اعتراضا على قدر أو يأسا من الحياة. إذ لا بأس ألا تكون بخير في بعض الأحيان، وهذ المشاعر هي جزء من الحياة، ومهما تتطلع بسعيك نحو الكمال لا بد أن تتقبل كونك ضعيفا، فإنك خلقت ناقصا ولن تكتمل أبدا، ولا يوجد على هذه الأرض إنسان كامل سعيد دائما؛ فالمفتاح للشفاء هو أن تتكيف مع تلك المشاعر وألا تنكرها.<br><br> غالبًا ما يخبرنا الآخرين أنه يجب علينا قمع هذه المشاعر، فنسمع منهم كلمات محبطة مثل  شوف بلاوي الناس تهون مصيبتك  وغيرها من الكلمات، والتي يشعرونني فيها بأني غير راضٍ بقضاء الله وحالي معهم كما يقول المثل (اللي ايده مو تحت الحجر ما بتوجعوا)، ولكن واجبك معهم أن تعمل على قول المثل (إذن من طين وإذن من عجين). لا بأس أن تعترف بهذه المشاعر وتتقبلها.  يقول المثل (دواء الدهر الصبر عليه) وأيضا (الصبر باب للمر حتى يمر) وأيضا (إذا غلي اللحم الصبر رخيص) وتلك هي خطوة أولى نحو فهم نفسك بشكل أفضل؛ لاستعادة فهمك للحياة واكتساب خبرات أقوى لمواجهة ظروفها. <h2><font color=$headerColor>ما يجب القيام به:</font></h2> <b><font color = $headerColor> ١. تقبل واعترف بمشاعرك:</font></b> من المهم أن تسمح لنفسك بتقبل مشاعرك دون الشعور بالذنب أو الخجل.<br> <b><font color = $headerColor> ٢. تحدث عن الأمر:</font></b> سواء مع صديق أو أحد أفراد الأسرة أو مستشار، فإن التحدث عن مشاعرك يمكن أن يوفر لك الراحة. لست مضطرًا إلى المرور بهذا بمفردك.<br> <b><font color = $headerColor> ٣.اسمح لنفسك بالبكاء أو الراحة:</font></b> في بعض الأحيان، قد تجتاحك المشاعر وهذا أمر طبيعي، فالبكاء أو أخذ الوقت لإعادة شحن طاقتك هي مراحل مهمة في الشفاء.<br> <b><font color = $headerColor> ٤.مارس الرعاية الذاتية:</font></b> شارك في الأنشطة التي تساعدك على الاسترخاء وإعادة شحن طاقتك، سواء كانت كتابة مذكرات أو الذهاب في نزهة؛ فالرعاية الذاتية تتيح لك التركيز على رفاهيتك.<br> <b><font color = $headerColor> ٥.طلب الدعم المهني:</font></b> إذا اجتاحك أو استمر معك الحزن أو الألم، فيمكن لمتخصص الصحة النفسية تقديم التوجيه، والأدوات والاستراتيجيات؛ لمساعدتك على التشافي.<br>"

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

    override fun onStop() {
        super.onStop()
        if (::textToSpeech.isInitialized){
            textToSpeech.release()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }


}