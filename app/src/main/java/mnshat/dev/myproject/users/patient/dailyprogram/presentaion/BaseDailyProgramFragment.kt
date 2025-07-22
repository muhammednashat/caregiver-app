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
"<p>دعنا نستلهم هذا التمرين من كلمات الدكتور مصطفى محمود:</p><p><font color='green'><b>إن مشكلتك ليست في السنوات التي ضاعت، ولكن سنواتك القادمة التي ستضيع حتما إذا واجهت الدنيا بنفس العقلية</b></font></p>التغيير الذي تبحث عنه في كل مكان وتنتظره من شخص آخر، هو في الحقيقة أنت من يملكه لذا ما عليك الآن فعله، هو أن تلتفت لتفكيرك وتبدأ في تغييره لصنع التغير الأعظم الذي تنتظره.<br><h2>خطوات التمرين:</h2><p><b>1.التعرف على الأفكار السلبية الحالية</b><br>اجلس في مكان هادئ<br>   اكتب 3 أفكار أو معتقدات سلبية تشعر أنها تعيق تقدمك.<br> مثال:<br>فات الأوان على التغيير.<br>-ليس لدي أي فرصة للنجاح.<br>-أخطائي السابقة دمرت مستقبلي.<br>الآن، اسأل نفسك:<font color='red'> من 100٪، ما مدى صحة هذه الفكرة؟</font><b>2.إعادة صياغة إحدى الأفكار السلبية</b><br>-اختر فكرة واحدة من الأفكار التي كتبتها.<br>-اسأل نفسك:<br> هل يمكنك تغيير هذه الفكرة إلى فكرة بديلة؟<br>أعد صياغة الفكرة بأسلوب إيجابي.<br>أمثلة<br>بدلًا من <font color='red'><b>فات الأوان على التغيير</b></font> → <font color='green'><b>التغيير ممكن. لقد تمكنت من إجراء تغييرات في الماضي</b></font></p><p>بدلًا من<font color='red'>لا أحد يهتم بي أو بما أفعله</font> → <font color='green'>هناك بعض الأشخاص الذين يهتمون بي</font></p><p>الآن، اسأل نفسك: <font color='red'>-من 100٪، ما مدى صحة هذه الفكرة؟</font>إذا بدت لك هذه الفكرة البديلة صحيحة إلى حد ما، انتقل إلى الخطوة 3</p><p><b><font color=’red’>-أعد تقييم فكرتك السلبية من 100٪. هل تغير تقييمك بعد أن اقترحت فكرة بديلة؟</b></p></font><b>3.اقترح عبارة إيجابية موجهة نحو الفعل</b><br>أمثلة:<br><p><b><font color='green'>يبدأ التغيير بخطوة صغيرة واحد<br>يمكنني البدء في بناء علاقات أقوى من خلال التواصل والتفاعل مع الآخرين</font></b></p><b>4.اتخاذ خطوة عملية صغيرة</b><br>فكر في خطوة بسيطة يمكنك اتخاذها اليوم لتعكس الفكرة الإيجابية الجديدة التي كتبتها.<br>مثال<br>إذا كانت فكرتك الجديدة:  <p><b><font color='green'>يمكنني بدء بناء علاقات أقوى من خلال المبادرة والتواصل مع الآخرين.</font></b></p><b>-فالخطوة التي يمكن ان تقوم بها:</b><br>  أرسل رسالة نصية لشخص تحبه واسأله عن يومه.<br> أو خطط للاتصال بصديق قديم لبضع دقائق.<br><br><b>5.تسجيل تقدمك ومشاعرك</b><br>في نهاية اليوم، سجل في الدفتر أو الورقة:<br>-ما الذي قمت به؟<br>  -كيف شعرت أثناء تنفيذ الخطوة؟<br>  -ما الذي تعلمته من هذه التجربة؟<br></b></p><br><p><b><font color='red'>\\uD83D\\uDCA1 ملاحظة: إذا كان تقييمك للفكرة السلبية مرتفعًا جدًا (قريبًا من 100٪) ولم يتغير في الخطوة 2 بعد اقتراحك لفكرة بديلة، أو إذا كانت فكرتك البديلة غير قابلة للتصديق بشكل كبير (قريبة من 0٪)، فقد تحتاج إلى التحدث مع شخص تثق به أو مع أخصائي في الصحة النفسية. </font></b></p><p><b>ضع عبارة مصطفى محمود في مكان تراه يوميًا (على الهاتف، أو على ورقة مكتوبة) لتذكرك بأهمية تغيير طريقة التفكير.</b></p>"

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