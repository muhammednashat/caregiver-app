package mnshat.dev.myproject.users.patient.libraraycontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentArticleBinding
import mnshat.dev.myproject.databinding.FragmentLibraryContentBinding
import mnshat.dev.myproject.factories.LibraryViewModelFactory
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.COMMON_CONTENT
import mnshat.dev.myproject.util.Customized_CONTENT
import mnshat.dev.myproject.util.LANGUAGE


class ArticleFragment : BasePatientFragment<FragmentArticleBinding>() {

    private lateinit var viewModel: LibraryViewModel

    override fun getLayout() = R.layout.fragment_article


    override fun initializeViews() {
        super.initializeViews()
        initViewModel()
        initializeView()
    }

    override fun setupClickListener() {
        super.setupClickListener()
        binding.icBack.setOnClickListener {
           findNavController().popBackStack()
        }
    }

    private fun initViewModel() {
        val factory = LibraryViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[LibraryViewModel::class.java]
    }

    private fun initializeView() {

        val index = viewModel.getCurrentContentIndex()
        val libraryContentList =
            when (viewModel.getCurrentContent()) {
                COMMON_CONTENT -> viewModel.mLibraryContentMostCommon
                else -> viewModel.mLibraryContentCustomized
            }
        val content = libraryContentList[index]
        setArticle(content)
        setTitles(content)
        binding.date.text = content.date

    }

    private fun setTitles(content: LibraryContent) {
     if (sharedPreferences.getString(LANGUAGE) ==  "en"){
         binding.title.text = content.enTitle
     }else{
         binding.title.text = content.arTitle
     }
    }

    private fun setArticle(content: LibraryContent) {
        if (sharedPreferences.getString(LANGUAGE) ==  "en"){
            binding.article.text = content.enText?.repeat(100)
        }else{
            binding.article.text = content.arText?.repeat(100)
        }
    }

}