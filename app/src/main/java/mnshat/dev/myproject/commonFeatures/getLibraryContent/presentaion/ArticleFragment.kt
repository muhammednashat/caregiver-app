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
        if (sharedPreferences.getString(LANGUAGE) == "en") {
            binding.title.text = content.enTitle
        } else {
            binding.title.text = content.arTitle
        }
    }

    private fun setArticle(content: LibraryContent) {
        val headerColor = Color.parseColor("#1565C0") // Blue color
        val textColor = Color.parseColor("#212121")
        binding.article.text = Html.fromHtml(
            "<b><font color='blue'>Free Yourself from Black-and-White Thinking and Face Life’s Challenges with a Flexible Mind</font></b><p><font color=$textColor>In our daily lives, we often face situations that make us feel as if we are living between two options only: success or failure, happiness or sadness, strength or weakness. This perspective is known as black-and-white thinking, where we fail to pay attention to the gradients that may hold solutions and new paths. Yet life, in reality, is not always as it seems to us.<br><center>And life is not the same every day;<br>Not all the days of youth are alike.</center>As Al-Mutanabbi (915–965 CE) said:<br><center>Not everything a man desires, he attains;<br>The winds blow contrary to what the ships wish.</center> </font> <p>These verses remind us that circumstances sometimes do not go as planned. The winds may confront the ships, but that does not mean the journey has ended. Instead, it is an invitation to view matters in a new light and to consider alternatives that we may overlook when confined to an all-or-nothing mentality.</p><p>Let us learn how to challenge these rigid thoughts and reframe them. Instead of saying, If I don’t achieve this goal, I’m a failure, we can say, Maybe I didn’t get the result I wanted, but I’ve made significant progress, and there are other opportunities ahead. This simple shift in thinking can open new doors and reduce feelings of pressure and frustration.</p><b>- Overcoming Feelings of Frustration:</b><br>We often feel frustrated when things don’t go as planned, believing that our effort was wasted. However, flexible thinking allows us to see that every experience, whether successful or not, is an opportunity to learn and grow. Life is not a single path. Every turn can be the beginning of something new, even if it’s not what we expected.<br><b>- How to Apply Flexible Thinking in Your Life:<br>1. Ask Yourself: Are There Other Ways to View This Situation?</b><br>When facing a challenge or setback, instead of drowning in negative thoughts, try to look at the situation from different perspectives. Ask yourself, What can I learn from this? or What’s the next step I can take?<br><b>2. Avoid Rigid Thinking:</b><br>All-or-nothing thinking quickly leads to frustration. Replace it with balanced thoughts, like: I may not have achieved everything I wanted, but I’ve made progress, or This may not be complete success, but it’s a step in the right direction.<br><b>3. Set Small, Realistic Goals:</b><br>Instead of focusing on achieving big goals all at once, break them into smaller steps. This helps you feel a sense of accomplishment with each step, boosting your confidence and reducing the pressure of immediate success.<>br<b>4. Learn to Accept Change:</b><br>Change is part of life, and resisting it increases stress and frustration. Instead of worrying about what you can’t change, focus on what you can do to adapt to new circumstances. As Al-Mutanabbi said:<br><p>The winds blow contrary to what the ships wish.</p>You can’t always control the direction of the wind, but you can adjust your sails to move in the best direction.<br><b>- Turning Challenges into Opportunities:</b><p>When you view every experience as an opportunity for growth rather than as success or failure, life becomes more flexible. The winds may be strong, but well-designed ships know how to adapt to storms. Every obstacle is an invitation to learn and adapt.</p><p>If things aren’t going as planned, it doesn’t mean the journey is over. It may be the beginning of a new path more amazing than you imagined. Life is not only about reaching the goal but also about how we navigate the journey.</p><p>In the toughest moments, life reveals our true capabilities. Being able to face challenges with a flexible mind and patient heart helps you overcome obstacles. Failure is not the end; it is part of the journey toward success.</p><p><b>Remember:</b><br>Success and failure are not absolute opposites. The path is full of opportunities in between. When you let go of black-and-white thinking and adopt flexible thinking, you’ll see that life is filled with chances and experiences that help you grow and evolve. Every experience, whether positive or negative, adds to your journey and makes you stronger and wiser. Embrace those experiences with patience and keep moving forward towards a better future.</p>"
                )

//        if (sharedPreferences.getString(LANGUAGE) == "en") {
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
                showToast("done")
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