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

            "\"<p><b><font color=$headerColor>Developing Problem-Solving Skills: The Art of Facing Challenges</font></b></p><p>Have you ever felt like your problems are too big to solve? Like you’re the only one struggling in this way? If your answer is yes, you’re not alone. As the poet said:</p>Do you not see that hardships are many,<br>And fate lies in wait for all mankind?<br>Who among us has escaped misfortune?<br>This path is one where you are not alone.<br><p>Let me share a truth that might change your perspective: the problem isn’t in the size of the challenge, but in how you think about it.</p><p>You are the one who determines the size of the problem you face. If you see your challenge as an insurmountable mountain, it will remain so. But if you choose to view that mountain as an opportunity to climb and discover your strength, the challenge transforms into a journey of learning and growth. The poet once said:</p>No hardship or calamity befalls me,<br>Except it elevates me and enhances my stature.<br><p><b><font color=$headerColor>The Importance of Developing Problem-Solving Skills</font></b></p><b><font color=$headerColor>- Clearer Thinking:</font></b> By mastering structured thinking, you can approach challenges in an organised and logical way, avoiding confusion and seeing the bigger picture.<br><b><font color=$headerColor>- Boosting Confidence: </font></b>Successfully solving problems strengthens your self-belief, reminding you of your ability to overcome obstacles.<br><b><font color=$headerColor>- Unlocking Creativity:</font></b> Freeing yourself from fear and tension allows your mind to explore innovative solutions you might not have seen before.<br><b><font color=$headerColor>- Time Management: </font></b>Shifting focus from worry to solutions helps you save time and energy, giving you more space to work towards your goals.<br><p><b><font color=$headerColor>Practical Steps to Develop Problem-Solving Skills<br><br>1. Analyse the Problem:</font></b><br>Think of the problem as a puzzle you need to understand piece by piece. Ask yourself:<br>- What happened?<br>- Why did it happen?<br>- Who is involved?<br>- Has this happened before?<br>- What are the effects?<br><p><b><font color=$headerColor>2. View the Problem from Different Perspectives:</font></b></p><p>Imagine explaining your issue to a close friend or seeing it through someone else’s eyes. This exercise broadens your perspective.</p><p><b><font color=$headerColor>3. 3.التخطيط للحلول:</font></b></p>oاكتب الحلول الممكنة كلها، حتى تلك التي تبدو  بسيطة أو سخيفة. <br>- oقيّم كل حل بناءً على جدواه وتأثيره.<br>- oاختر الحل الأكثر واقعية وابدأ بتجربته.<br><br><p><b><font color=$headerColor>4. التشاور وطلب المساعدة:</font></b></p>ليس من الضعف أن تسأل الآخرين عن آرائهم. أحيانًا، قد تكون الإجابة التي تحتاجها في تجربة شخص آخر.<br><br><p><b><font color=$headerColor>5. التعلم من التجربة:</font></b></p>5.بعد حل المشكلة، خذ لحظة لتقييم ما تعلمته. كيف يمكنك استخدام هذا الدرس في المستقبل؟<br><br><p><b><font color=$headerColor>مثال :</font></b></p><p>تخيل أنك فقدت وظيفتك فجأة. في البداية، تشعر بالصدمة والخوف، وربما الغضب. لكن بدلاً من البقاء في هذه الحالة قررت حينها أن تأخذ خطوات صغيرة<br>- \uF0B7تحليل الوضع: لماذا فقدت الوظيفة؟ هل هناك مهارات تحتاج إلى تطوير؟<br>\uF0B7البحث عن الحلول: تحديث سيرتك الذاتية، والتواصل مع شبكتك المهنية، والبحث عن فرص تدريب جديدة.<br>\uF0B7تنفيذ الخطة: التقديم على وظائف، وحضور ورش عمل، واستثمار الوقت في تحسين مهاراتك.<br>بعد أشهر، تجد نفسك في وظيفة أفضل، واكتسبت مهارات وثقة لم تكن تمتلكها من قبل. وتذكر أن ما حدث وكأنه نهاية، قد تحول إلى بداية جديدة.</p><p><b><font color=$headerColor>تذكر: كل مشكلة تواجهها تحمل في طياتها فرصة للتعلم والنمو.</font></b></p>تطوير مهارات حل المشكلات ليس فقط لإيجاد حلول، بل عن بناء عقلية قوية تواجه التحديات بشجاعة وهدوء.<p><b><font color=$headerColor>آمن بنفسك، وثق أنك تملك القوة لتجاوز كل ما تواجهه.</font></b></p>"
//                       <p><b><font color=$headerColor$headerColor>
        //                    </font></b></p>
        //                    <p>
        //                    </p>

            //                <br>
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