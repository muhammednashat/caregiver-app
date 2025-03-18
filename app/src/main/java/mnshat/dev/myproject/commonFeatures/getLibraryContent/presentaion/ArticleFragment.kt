package mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion

import android.graphics.Color
import android.text.Html
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.commonFeatures.posts.ChooseSupporterFragment
import mnshat.dev.myproject.databinding.FragmentArticleBinding
import mnshat.dev.myproject.interfaces.OnSendButtonClicked
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.LIBRARY


class ArticleFragment : BaseLibraryFragment<FragmentArticleBinding>(), OnSendButtonClicked {


    override fun getLayout() = R.layout.fragment_article


    override fun initializeViews() {
        super.initializeViews()
        initializeView()
    }


    private fun initializeView() {
        val content = viewModel.getContent()
        setArticle(content)
        setTitles(content)
        binding.date.text = content.date

    }

    private fun setTitles(content: LibraryContent) {
        if (sharedPreferences.getString(LANGUAGE) ==  "en" ) {
            binding.title.text = content.enTitle
        } else {
            binding.title.text = content.arTitle
        }
    }

    private fun setArticle(content: LibraryContent) {



        val headerColor = Color.parseColor( "#204167") // Blue color
        val textColor = Color.parseColor( "#204167")
//        val textColor = Color.parseColor( "#212121")
        //                  <p><b><font color=$headerColor$headerColor$headerColor></font></b></p>
        binding.article.text = Html.fromHtml(
            "<p><b><font color=$headerColor>التعرف على قيمة العناية الذاتية</font></b></p><p>دعنا نبدأ برسالة مهمة: مشاعرك طبيعية تمامًا، وما تمر به ليس ضعفًا أو عيبًا، فما يحدث معك قد يحدث مع الآخرين، وفهمك لهذه المشاعر هو أول خطوة نحو إدارتها بوعي وسلام.</p><p>والحياة مليئة بالتحديات والتقلبات التي قد لا نستطيع التحكم بها أو تجنبها، لكننا نملك القوة لتجهيز أنفسنا لمواجهتها. هذه القوة تكمن في العناية الذاتية، وهي ليست رفاهية أو خيارًا إضافيًا، بل هي ضرورة تساعدك على خوض معارك الحياة بثبات ومرونة.</p><p>قال الشاعر:</p>لَيسَت حَياتَكَ غَيرَ ما صَوَّرتَها<br>أَنتَ الحَياةُ بِصَمتِها وَمَقالِها<br><p>فعندما يكون الجسم ضعيفا يكون أكثر عرضة للتأثر بالطاقة السلبية الناتجة عن أي موقف عكسي أو توتر سريع أو انفعال عالي، وهنا يعود السبب لكون الجسم مرتبط باستقرارك النفسي تمامًا، وكلما اهتممت بالرعاية الذاتية التي يستحقها جسمك وعقلك وطاقتك؛ كلما أصبحت حياتك أكثر استقرارا وتقبلا ومرونة، كما تساعدك على التفكير بمنطق بدلاً من التوتر والانفعال.</p><p>قال الشاعر:</p>ثلاثةُ أيّامٍ هيَ الدّهرُ كلّه<br>وما هُنّ غيرَ الأمسِ واليومِ والغَدِ<br>وما البَدرُ إلاّ واحِدٌ غيرَ أنّه<br>يَغِيبُ ويأتي بالضّياءِ المُجَدِّدِ<br><p><b><font color=$headerColor>مثال:</font></b></p>Imagine you’re working on an important project or handling a demanding workload. You feel exhausted, and your thoughts are racing, making it difficult to focus or know where to begin. Instead of forcing yourself to keep going under pressure, you decide to pause.</p><p>You step outside for a 10-minute walk, breathe in some fresh air, or take a moment to enjoy the beauty of a sunset or the trees around you. These few minutes can bring a sense of calm, help you organise your thoughts, and allow you to return to your tasks with renewed energy and focus.</p><p><b><font color=$headerColor>The Key Message here:</font></b></p>Self-care doesn’t have to take hours or require grand gestures. Sometimes, even the simplest steps, like stepping away momentarily, can provide a fresh perspective and much-needed relief, enabling you to move forward with greater strength and clarity.</p><p><b><font color=$headerColor>Why is Self-Care Essential?</font></b></p>- Mind-Body Connection: Your physical health has a direct impact on your mental stability. A strong body gives you the energy to tackle everyday challenges with confidence.- Building Emotional Resilience: Each act of self-care strengthens your ability to face life’s difficulties with calm and thoughtful responses, rather than being stressed and overwhelmed.</p><p><b><font color=$headerColor>خطوات بسيطة لرعاية ذاتك:</font></b></p><p><b><font color=$headerColor>1. الحصول على قسط كافٍ من النوم:</font></b></p>النوم ليس رفاهية، بل هو أساس لصحة العقل والجسم، وقلة النوم تقلل من تركيزك وقدرتك على اتخاذ قرارات سليمة؛ لذا امنح نفسك النوم الكافي لتواجه يومك بطاقة وتجدد.<p><b><font color=$headerColor>2. التغذية السليمة:</font></b></p>2.ما يتناوله جسمك يؤثر على طاقتك ومزاجك. فنقص الفيتامينات وسوء التغذية قد يؤديان إلى مشاعر التوتر والإرهاق، وأما تناول الطعام المتوازن فإنه يمنحك القوة للاستمرار.<p><b><font color=$headerColor>3. ممارسة الرياضة:</font></b></p>النشاط البدني ليس للعناية بالجسم فقط، بل هو وسيلة لتحريرك من التوتر. والرياضة تطلق الإندورفين، وهو هرمون السعادة؛ مما يعزز شعورك بالرفاهية، ويزيد من قدرتك على مواجهة المواقف الصعبة.<p><b><font color=$headerColor>4.الانخراط في الهوايات:</font></b></p>خصص وقتًا لما تحب، فممارسة الهوايات تفرغ الطاقة السلبية بداخلك، وكن أنت في اللحظات التي تستهويك.<p><b><font color=$headerColor>5.طلب الدعم الاجتماعي:</font></b></p>لا تخجل من طلب المساندة من أحبائك من عائلتك، أو أصدقائك، أو شريك حياتك. فوجود أشخاص يفهمونك ويدعمونك يمكن أن يكون مصدر قوة في الأوقات الصعبة.</p><p><b><font color=$headerColor>تذكر</font></b></p><p> أن العناية الذاتية ليست أنانية، بل هي حب تجاه نفسك، واستثمار في صحتك ورفاهيتك، ومع كل خطوة صغيرة تأخذها نحو رعاية ذاتك؛ فإنك تبني درعًا يحميك من تقلبات الحياة</p><p><b><font color=$headerColor>اختر نفسك دائمًا. كن لطيفًا مع جسدك وعقلك، لأنك تستحق أن تعيش حياة مليئة بالسلام والقوة.</font></b></p>"
            // input =>    => out
//                    "<p><b><font color=$headerColor$headerColor></font></b></p>"+
//                    "<p><b><font color=$headerColor$headerColor$headerColor></font></b></p>"+
//                    "<p></p>" +
//                    "<p></p>" +
//                    "<p></p>" +
//                    "<p></p>" +
//                    "<p></p>" +
//                    "<p></p>" +
//                    "<><><>" +
//                    "<><><>" +
//                    "<><><>" +
//                    "<><><>"
        )

//        if (sharedPreferences.getString(LANGUAGE) ==  en ) {
//
//            binding.article.text = content.enText // repeat the text 100 times
//        } else {
//            binding.article.text = content.arText// repeat the text 100 times
//        }

    }


    override fun setupClickListener() {
        super.setupClickListener()

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.share.setOnClickListener {
            if (sharedPreferences.getBoolean(HAS_PARTNER)){
                val fragment = ChooseSupporterFragment()
                fragment.initOnConfirmButtonClicked(this)
                fragment.show(childFragmentManager, ChooseSupporterFragment::class.java.name)
            }else{
                showToast(getString(R.string.no_supporters_text))
            }
        }

        binding.suggest.setOnClickListener {
            displaySuggestedContent(this, getString(R.string.suggest_other_articles), ARTICLE)
        }

    }

    override fun onItemClicked(type: String, index: Int, content: String) {
        super.onItemClicked(type, index, content)
        initializeView()
    }


    override fun onSendClicked(list: MutableList<String>) {
        showProgressDialog()
        viewModel.shareContent(post(list)) {
            if (it == null) {
                showToast( "done" )
            } else {
                showToast(it)
            }
            dismissProgressDialog()
        }

    }

    fun post(list: MutableList<String>) =
        Post(
            type = LIBRARY,
            libraryContent = viewModel.getContent(),
            supporters = list
        )


}