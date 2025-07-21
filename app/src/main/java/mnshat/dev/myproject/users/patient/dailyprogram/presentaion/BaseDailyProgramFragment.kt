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
import mnshat.dev.myproject.util.TextToSpeechUtil

@AndroidEntryPoint
open class BaseDailyProgramFragment : BaseFragment() {

    val viewModel: DailyProgramViewModel by viewModels()
    lateinit var textToSpeech: TextToSpeechUtil
    lateinit var htmlText: String

    private var _fragment = ""
    lateinit var binding: LayoutTaskBinding
    lateinit var task: Task
    var player: ExoPlayer? = null

    fun getTaskFromList(index: Int, numberTask: Int) {
        showProgressDialog()
        viewModel.listOfTasks.let { listOfTasks ->
            task = listOfTasks[index]
            task.let {
                val currentLang = viewModel.sharedPreferences.getString(LANGUAGE)
                if (currentLang != ENGLISH_KEY) {
                    setText(numberTask, task.arTitle, task.arDescription)
                } else {
                    setText(numberTask, task.enTitle, task.enDescription)
                }
                checkType(task.type)
            }
        }
    }

    private fun setText(numberTask: Int, title: String?, description: String?) {
        if (title?.isEmpty() == true) {
            binding.textTitle.visibility = View.GONE
        } else {
            if (_fragment == "BehaviouralActivationFragment") {
                binding.textTitle.text = title
            } else {
                binding.textTitle.text = title
            }
        }
        setDescriptionText(description)

    }




//    "<h2><font color=$headerColor>ما أشعر به أحيانًا :</font></h2> أشعر أحيانًا أني لا أرى من ألوان الحياة إلا الأبيض والأسود. أشعر بألم ينهك روحي وتفكيري. إنه يحق لي أن أعيش بسعادة، فشعوري بهذا الحزن والألم يعني أن هناك خطأ ما فيّ، ولا أعرف ماذا أفعل. <h2><font color=$headerColor>ما أحتاج إلى معرفته؟</font>   </h2> لا بأس على الإطلاق أن تشعر بالضيق أو الحزن أو الألم. فهذه مشاعر ذات طابع إنساني لا تعيب صاحبها، ولا تعني اعتراضا على قدر أو يأسا من الحياة. إذ لا بأس ألا تكون بخير في بعض الأحيان، وهذ المشاعر هي جزء من الحياة، ومهما تتطلع بسعيك نحو الكمال لا بد أن تتقبل كونك ضعيفا، فإنك خلقت ناقصا ولن تكتمل أبدا، ولا يوجد على هذه الأرض إنسان كامل سعيد دائما؛ فالمفتاح للشفاء هو أن تتكيف مع تلك المشاعر وألا تنكرها.<br><br> غالبًا ما يخبرنا الآخرين أنه يجب علينا قمع هذه المشاعر، فنسمع منهم كلمات محبطة مثل  شوف بلاوي الناس تهون مصيبتك  وغيرها من الكلمات، والتي يشعرونني فيها بأني غير راضٍ بقضاء الله وحالي معهم كما يقول المثل (اللي ايده مو تحت الحجر ما بتوجعوا)، ولكن واجبك معهم أن تعمل على قول المثل (إذن من طين وإذن من عجين). لا بأس أن تعترف بهذه المشاعر وتتقبلها.  يقول المثل (دواء الدهر الصبر عليه) وأيضا (الصبر باب للمر حتى يمر) وأيضا (إذا غلي اللحم الصبر رخيص) وتلك هي خطوة أولى نحو فهم نفسك بشكل أفضل؛ لاستعادة فهمك للحياة واكتساب خبرات أقوى لمواجهة ظروفها. <h2><font color=$headerColor>ما يجب القيام به:</font></h2> <b><font col<br> او= $headerColor> ١. تقبل واعترف بمشاعرك:</font></b> من المهم أن تسمح لنفسك بتقبل مشاعرك دون الشعور بالذنب أو الخجل.<br> <b><font color = $headerColor> ٢. تحدث عن الأمر:</font></b> سواء مع صديق أو أحد أفراد الأسرة أو مستشار، فإن التحدث عن مشاعرك يمكن أن يوفر لك الراحة. لست مضطرًا إلى المرور بهذا بمفردك.<br> <b><font color = $headerColor> ٣.اسمح لنفسك بالبكاء أو الراحة:</font></b> في بعض الأحيان، قد تجتاحك المشاعر وهذا أمر طبيعي، فالبكاء أو أخذ الوقت لإعادة شحن طاقتك هي مراحل مهمة في الشفاء.<br> <b><font color = $headerColor> ٤.مارس الرعاية الذاتية:</font></b> شارك في الأنشطة التي تساعدك على الاسترخاء وإعادة شحن طاقتك، سواء كانت كتابة مذكرات أو الذهاب في نزهة؛ فالرعاية الذاتية تتيح لك التركيز على رفاهيتك.<br> <b><font color = $headerColor> ٥.طلب الدعم المهني:</font></b> إذا اجتاحك أو استمر معك الحزن أو الألم، فيمكن لمتخصص الصحة النفسية تقديم التوجيه، والأدوات والاستراتيجيات؛ لمساعدتك على التشافي.<br>"

    /**
     * ١
     * ٢
     * ٣
     * ٤
     * ٥
     * ٦
     */


    private fun setDescriptionText(description: String?) {
        val headerColor = "#204167"

//        binding.textDescription.text = Html.fromHtml(description )

        binding.textDescription.text = Html.fromHtml(
            "<p>هذا التمرين مستوحى من كلمات مفعمة بالإيمان واليقين بعظمة الله، يهدف إلى توجيه عقلك وروحك نحو تسليم الأعباء لله، وتعزيز السلام الداخلي، وإعادة صياغة الأفكار السلبية عند مواجهة التحديات.</p><font color='green'><b><p><font color='green'>الله أكبر الله أكبر</font></p><p><font color='green'>من كل شعور يقضي على طمأنينتك</font></p><p><font color='green'>الله أكبر من الأحزان والقل</font></p><p><font color='green'>من أن تُظلم أو تُكسر أو تُسلَب</font></p><p><font color='green'>الله أكبر دائمًا وأبدًا من كل ما نحمل </font></p><p><font color='green'>ومن كل ما نظن ونعتقد، كبيرًا بلا مد </font></p></b></font><h2>خطوات التمرين:</h2><b>1.تهيئة عقلك ومساحتك:</b><br><p>اختر مكانًا هادئًا ومريحًا حيث تشعر بالأمان<br>اجعل نيتك أن تستغل هذا الوقت لإعادة الاتصال بالله واستعادة السلام في قلبك<br></p><p><b>2.التنفس والارتكاز:</b></p>-اجلس بوضعية مريحة مع استقامة ظهرك، سواء على كرسي أو على الأرض.<br>-أغمض عينيك وخذ أنفاسًا بطيئة ومتعمدة:<br>  استنشق من أنفك لمدة 4 ثوانٍ.<br>احبس أنفاسك لمدة ثانيتين<br>ازفر من فمك ببطء لمدة 6 ثوانٍ<br>-كرر هذا النمط من التنفس ثلاث مرات لتهدئة نفسك وتركيز ذهنك.<br><br><p><b>3.تكرار تأكيدات العظمة:</b></p>أثناء التنفس، كرر داخليًا بهدوء <font color='green'><b>-الله أكبر، أكبر من أي شعور يعكر صفو سلامي. الله أكبر، أكبر من الحزن والقلق.</b></font><br>-ركز على هذه الكلمات، ودع معناها يملأك بالهدوء والقوة.<br><b><p>4. ممارسة التسليم (إعادة الصياغة</p></b>-تخيل مخاوفك، صراعاتك، وشكوكك كحقيبة ثقيلة تُثقل كاهلك.<br>بصدق نية، قل داخليًا :<font color='green'><b>-: يا الله، أسلمك أعبائي، فأنت أكبر من كل ما أحمله</b></font><br>-تخيل نفسك تسلم هذه الحقيبة الثقيلة لله، واضعًا كل مخاوفك بين يديه بثقة كاملة برحمته وقوته اللامتناهية.<br>-أثناء ذلك، استشعر أن هذا الثقل يرفع من كتفيك، وأنك تصبح أخف وزنًا وأكثر حرية وهدوءًا.<br><p><b>5.التأمل والختام:</b></p><br>-بعد 5-10 دقائق، افتح عينيك ببطء وخذ لحظة للتفكر في شعورك.<br>-اختتم بدعاء بسيط: اللهم ارزقني السلام، والقوة، والهداية برحمتك وعظمتك.<br>ختياري: اكتب مشاعرك أو مخاوفك مستخدمًا العبارة:<br>الله أكبر من...[على سبيل المثال: خوفي، حزني، شكوكي[.<br>يساعدك هذا على تعزيز فعل تسليم أمورك لله وتحرير نفسك من القلق.<br>"
        )

    }














    fun changeColorOfTaskImage(status: Int?, root: ConstraintLayout, image: ImageView) {
        when (status) {
            1 -> {
                image.setImageResource(R.drawable.ic_check_blue)
                root.setBackgroundResource(R.drawable.circle_blue_dark)
            }
            2 -> {
                val params = root.layoutParams
                params.width = 115
                params.height = 115
                root.layoutParams = params
                root.setBackgroundResource(R.drawable.circle_orange_with_border)
            }
        }
    }


    fun hideSpiritualIcon(constraintTask: ConstraintLayout, line: View) {
        if (!viewModel.userProfile.religion!!) {
            constraintTask.visibility = View.GONE
            line.visibility = View.GONE
        }
    }

    fun witchFragment(fragment: String) {
        _fragment = fragment
    }

    fun getNextTask(index: Int, numberTask: Int): Int {
        player?.pause()
        return if (viewModel.listOfTasks != null) {
            val currentTaskIndex = (index + 1) % viewModel.listOfTasks.size
            getTaskFromList(currentTaskIndex, numberTask)
            currentTaskIndex
        } else 0
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

    fun showDescriptionDialog(image: Int, title: String, text: String) {

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
        dialogBinding.text.text = text

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
        player?.pause()

        if (::textToSpeech.isInitialized) {
            textToSpeech.release()
        }
        if (viewModel.isSyncNeeded){
            viewModel.updateCurrentTaskRemotely()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }


}