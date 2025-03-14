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
        val headerColor = Color.parseColor( "#1565C0") // Blue color
        val textColor = Color.parseColor( "#212121")
        binding.article.text = Html.fromHtml(
"<p><b><font color='blue'>Stay Resilient in the Face of Life’s Challenges and Accept What You Cannot Change</font></b></p>In the journey of life, we all encounter situations that make us feel weak or lost. Challenges are an integral part of this journey, but true strength lies in accepting what cannot be changed and finding peace in the face of destiny. As Imam Al-Shafi'i (767-820 AD) said:<br><br>Let days go forth and do as they please<br>And remain firm when settled is the Decree<br>Don’t be afraid of what happens by night<br>For the affairs of this world are not to last<br><p><b><font color='blue'>- Managing Negative Thoughts: Reassessing Reality</font></b><br>When we feel stressed or sad, negative thoughts tend to dominate our minds. We might find ourselves caught in a spiral of thinking like Things will never get better or Everything is always against me. This is where Cognitive Behavioural Therapy (CBT) helps us reassess those thoughts. Ask yourself:<br>Is this thought realistic? Have I faced similar situations before and seen things improve?<br><p>As Al-Shafi'i says:</p>No sadness lasts forever, nor any happiness<br>And you shall not remain in poverty, or any luxury<br></p><p>Positive thinking begins by realizing that nothing lasts forever, neither sorrow nor joy. Learn to acknowledge negative thoughts without surrendering to them and remember that patience is the key to navigating life’s storms.</p><p><b><font color='blue'>- Contentment and Inner Strength: Your Path to Inner Peace</font></b><br>In a world that demands so much from us, we sometimes feel like we need more to be happy. But true peace comes from contentment. As Imam Al-Shafi'i teaches:<br>If, in your heart, you possess contentment<br>Then you and those who possess the world are equal<br></p><p>In the context of CBT, contentment arises from focusing on what we have today, rather than worrying about what we lack. This doesn’t mean giving up on ambition but finding a balance between striving and being satisfied. When we are content with what we have, we open the door to mental calmness and close the door to feelings of failure.</p><p><b><font color='blue'>- Dealing with Self-Criticism: Be Compassionate to Yourself</font></b><br>We are often our harshest critics. We may punish ourselves for every mistake or failure. But self-compassion is the key to freeing ourselves from this burden. As Imam Al-Shafi'i said:<br><br>If your faults become too much in front of the people<br>Then know that kindness covers all faults<br>And how many faults are kept hidden by kindness!<br></p><p>In CBT, we use the technique of challenging self-criticism: Is the criticism I am directing at myself logical? Could I be more compassionate towards myself, just as I would be with others? Self-compassion gives you the opportunity to grow and learn, rather than drowning in feelings of failure.</p><p><b><font color='blue'>- Trusting in Divine Will and Adapting to Circumstances: Building Mental Resilience</font></b></p><p>We live in a world where we cannot control every detail. Imam Al-Shafi'i reminds us that certain destinies are unavoidable, and we must trust in God’s wisdom. Nothing can change what has been decreed for us, but what we can control is how we respond to life’s events.</p>And for him upon whose horizon death descends,<br>No earth can offer him protection, nor any sky<br>Indeed, the earth of Allah is certainly vast<br>But if decree descends, then the world constricts<br><p>Mental resilience is the ability to face challenges and tough situations without breaking down. This begins by accepting that some things are beyond our control and looking for ways to adapt to what happens. Acceptance doesn’t mean giving up but moving forward with confidence and calmness no matter the circumstances.<p></p>Imam Al-Shafi'i’s poem carries timeless wisdom, reinforced by the principles of CBT. By accepting life’s fluctuations, managing negative thoughts, and practising self-compassion, we can build inner strength that helps us navigate even the toughest situations. Always remember that life brings a mixture of joys and sorrows, and contentment and trust in God’s wisdom will lead you to inner peace and the ability to adapt to whatever comes your way.</p>"
        )
//        if (sharedPreferences.getString(LANGUAGE) ==  en ) {
//
//            binding.article.text = content.enText?.repeat(100) // repeat the text 100 times
//        } else {
//            binding.article.text = content.arText?.repeat(100) // repeat the text 100 times
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