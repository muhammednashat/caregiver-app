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
            "<p><b><font color=$headerColor>Finding Meaning in Small Achievements</font></b></p><p>In a world that glorifies grand accomplishments and ties self-esteem to enormous successes and dazzling appearances, you may sometimes feel like you're falling behind in the race of life. What you've achieved might seem small or invisible. Yet, the truth that many overlook is that small achievements are not merely simple steps; they are the essential building blocks of true success and tell a story of persistence and determination.</p><p>As the poet said:</p>A man is defined by his relentless pursuit,<br>Of lofty goals, with courage resolute.<br>He who succumbs to sloth and fear,<br>Will find his ambitions fade, disappear,<br>Even if blessed with wealth and lineage high,<br>For without effort, all dreams will die.<br>But one who dons the armour of resolve each day,<br>And strides with determination, will find his way.<br><p>You might compare yourself with others and find yourself engulfed in feelings of inadequacy or frustration, wondering: Why can’t I achieve what others have? So, let me share a truth that may change your perspective: Achievement is not about the size of what you accomplish but about the journey itself, its challenges, difficulties, and the lessons it teaches you.</p><p>Imagine starting a journey to learn a new language. At first, the words seem unfamiliar, and the rules complex. You make countless attempts, facing constant frustration. Then, after weeks of effort, you write a simple, error-free sentence. To others, this might seem trivial, but only you know how many mistakes you made, how often you felt like giving up. This small achievement signifies that you persevered, learnt, and improved.</p><p><b><font color=$headerColor>Why are Small Achievements Important?</font></b></p>- Their accumulation makes a difference: Small achievements are puzzle pieces that complete the bigger picture. You can’t reach the summit without first taking the initial steps.<br>- They reflect your personal progress: Each small achievement is proof of growth and development, showing that you are challenging yourself and steadily moving towards your goals.<br>- They boost self-confidence: When you give your small achievements the recognition they deserve, you begin to see yourself in a new light filled with pride and self-appreciation.<br><p><b><font color=$headerColor>A Call for Reflection</font></b></p>Think about your daily accomplishments that might seem simple, such as preparing a healthy meal after a long day, finishing a page of a book you've been putting off, or even just getting out of bed on a particularly heavy day. Each of these moments is a testament to your strength and resilience.</p><p><b><font color=$headerColor>الرسالة:</font></b></p><p><b><font color=$headerColor>اجعل لكل إنجاز صغير معنى، وامنحه قيمة؛ لأنه جزء من رحلتك الفريدة التي لا يشبهها أحد.</font></b></p><p>لا تدع العالم يُملي عليك تعريف النجاح، فإنجازاتك الصغيرة هي وقود رحلتك، وهي تستحق منك كل التقدير والاعتزاز، واجعل لكل خطوة معنى، لأن في نهاية الأمر أنت الشخص الوحيد الذي يفهم قيمتها الحقيقية.</p>"

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