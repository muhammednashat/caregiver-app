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
        //                  <p><b><font color=$headerColor$headerColor></font></b></p>
        binding.article.text = Html.fromHtml(
            ""
            // input =>    => out
//            "<p><b><font color=$headerColor>النوم والاتزان العاطفي</font></b></p><p>النوم ليس مجرد حالة من الراحة الجسدية؛ إنه مفتاح التوازن النفسي والعقلي، وضروري للحفاظ على الصحة العامة والمزاج الجيد. عندما لا نحصل على قسط كافٍ من النوم، يبدأ التأثير السلبي في الظهور على الأفكار، المشاعر، أو السلوك.</p><p><b><font color=$headerColor>كما قال المتنبي</font></b>وإذا كانتِ النُّفوسُ كِبارا<br>تَعِبَتْ في مُرادِها الأجسام<br><p>هذا البيت يذكرنا بأن الطموح الكبير والتحديات التي تواجهنا قد تجهد أجسامنا وعقولنا. ولكن علينا تجديد طاقة العقل والجسد لتحقيق طموحاتنا الكبيرة وألا نتعرض للضغوط التي تؤثر على حالتنا النفسية وهذه هي وظيفة النوم. </p><p><b><font color=$headerColor>تأثير قلة النوم وتقلبه على المزاج والأفكار والسلوك وكيف يؤدي إلى الاكتئاب</font></b></p><p>تؤدي قلة النوم وتقلبه إلى تدهور جسدي ونفسي، ويصبح الشخص أكثر عرضة للغضب، والتوتر، والقلق. وغيرها الكثير من العوارض السلبية وإذا استمرت هذه الحالة لفترة طويلة، فإن هذه التغييرات تضع الشخص في دائرة مغلقة من مما يزيد من احتمالية الإصابة بالاكتئاب. ومما لا شك به أن النوم هو عامل أساسي لتنظيم العواطف والتعامل مع التوتر، وعندما يُحرم منه الجسم والعقل، يصبح من الصعب الحفاظ على الصحة النفسية.</p><p><b><font color=$headerColor>كيفية تحسين النوم</font></b></p><p>يمكننا تحسين جودة نومنا من خلال بعض التغييرات البسيطة. إليك بعض النصائح:</p><p><b><font color=$headerColor>تحدي الأفكار السلبية عن النوم:</font></b></p><p>إذا كنت تعتقد أن النوم يضيع الوقت أو يعطل الإنتاجية، حاول تغيير هذه الفكرة. النوم ليس تعطيلًا، بل هو الأساس الذي تقوم عليه الإنتاجية. فكر بهذه الطريقة: النوم يجعلني أكثر كفاءة وإنتاجية غدًا.</p><p><b><font color=$headerColor>إنشاء روتين ثابت للنوم:</font></b></p><p>الالتزام بروتين منتظم للنوم يساعد على تنظيم ساعتك البيولوجية. اذهب إلى الفراش واستيقظ في نفس الوقت كل يوم. هذا الروتين الثابت يضمن شعورك بالنشاط صباحًا.</p><p><b><font color=$headerColor>التخلص من الأفكار السلبية قبل النوم:</font></b></p><p>قبل النوم، من الضروري تهدئة العقل. حاول ممارسة بعض تمارين الاسترخاء أو قراءة الأدعية أو القرآن الكريم.</p><p><b><font color=$headerColor>ابتعد عن الأجهزة الإلكترونية قبل النوم:</font></b></p><p>4.الضوء الأزرق المنبعث من الهواتف وأجهزة الكمبيوتر يؤثر على قدرة العقل على الاسترخاء. حاول الابتعاد عنها بوقت كاف قبل النوم.</p><p><b><font color=$headerColor>تذكر دائمًا </font></b></p><p>النوم ليس مجرد راحة للجسم، بل هو شفاء للعقل والروح. من خلال النوم الجيد، نستطيع تحسين مزاجنا، تعزيز طاقتنا، واتخاذ قرارات أكثر حكمة.</p><p>اعطِ نفسك الفرصة للاستمتاع بالنوم، ليكون عقلك مستعدًا لمواجهة تحديات الغد بروح هادئة ونشاط متجدد.</p><p><b><font color=$headerColor>تذكر قول الشاعر: </font></b></p><p>إِنّي وَإِن كانَ جَمعُ المالِ يُعجِبُني<br>ما يَعدُلُ المالُ عِندي صَحَّةَ الجَسَدِ<br>المالُ زَينٌ وَفي الأَولادِ مَكرُمَةٌ<br>وَالسُقمُ يُنسيكَ ذِكرَ المالِ وَالوَلَدِ<br><br>"
//                    "<p><b><font color=$headerColor></font></b></p>"+
//                    "<p><b><font color=$headerColor$headerColor></font></b></p>"+
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