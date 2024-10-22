package mnshat.dev.myproject.commonFeatures.libraraycontent

import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.auth.AgeFragment
import mnshat.dev.myproject.commonFeatures.sharingcontent.ChooseSupporterFragment
import mnshat.dev.myproject.databinding.FragmentArticleBinding
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.LANGUAGE


class ArticleFragment : BaseLibraryFragment<FragmentArticleBinding>() {



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
        if (sharedPreferences.getString(LANGUAGE) == "en") {

            binding.article.text = content.enText?.repeat(100) // repeat the text 100 times
        } else {
            binding.article.text = content.arText?.repeat(100) // repeat the text 100 times
        }
    }


    override fun setupClickListener() {
        super.setupClickListener()

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.share.setOnClickListener {




            ChooseSupporterFragment().show(childFragmentManager, ChooseSupporterFragment::class.java.name)






        }

        binding.suggest.setOnClickListener {
            displaySuggestedContent(this, getString(R.string.suggest_other_articles),ARTICLE)
        }

    }

    override fun onItemClicked(type: String, index: Int, content: String) {
        super.onItemClicked(type, index, content)
        initializeView()
    }
}