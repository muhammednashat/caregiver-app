package mnshat.dev.myproject.posts.presentation

import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentDisplayArticleBinding
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.users.patient.BasePatientFragment
import mnshat.dev.myproject.util.LANGUAGE

class DisplayArticleFragment : BasePatientFragment<FragmentDisplayArticleBinding>() {

    override fun getLayout() = R.layout.fragment_display_article


    override fun initializeViews() {
        super.initializeViews()
        initializeView()
    }

    private fun initializeView() {
    val libraryContent = DisplayArticleFragmentArgs.fromBundle(requireArguments()).libraryContent
        setArticle(libraryContent)
        setTitles(libraryContent)
//        binding.date.text = libraryContent.date

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
            binding.article.text = content.arTitle?.repeat(100) // repeat the text 100 times
        }
    }

}