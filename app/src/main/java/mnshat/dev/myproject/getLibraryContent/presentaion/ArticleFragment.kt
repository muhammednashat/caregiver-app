package mnshat.dev.myproject.getLibraryContent.presentaion

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.posts.presentation.ChooseSupporterFragment
import mnshat.dev.myproject.databinding.FragmentArticleBinding
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.interfaces.OnItemLibraryContentClicked
import mnshat.dev.myproject.posts.OnSendButtonClicked
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.LIBRARY
import mnshat.dev.myproject.util.TextToSpeechUtil
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log


class ArticleFragment : BaseFragment(), OnSendButtonClicked, TextToSpeech.OnInitListener, OnItemLibraryContentClicked {


    private val viewModel: LibraryViewModel by activityViewModels()
    private lateinit var binding: FragmentArticleBinding
    private lateinit var htmlText: String
    private lateinit var textToSpeech: TextToSpeechUtil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentArticleBinding.inflate(inflater, container, false)
        setupClickListener()
        initializeView()
        return binding.root

    }


    private fun initializeView() {
        textToSpeech =  TextToSpeechUtil(TextToSpeech(requireActivity(), this))
        val content = viewModel.getContent()
        binding.container.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(content.backgroundColor));
        setArticle(content)
        setTitles(content)
        loadImage(requireActivity(), content.imageURL, binding.imageView)
        loadImage(requireActivity(), content.imageURL, binding.imageView2)
    }

    private fun setTitles(content: LibraryContent) {
        if (viewModel.sharedPreferences.getString(LANGUAGE) == "en") {
            binding.title.text = content.enTitle
        } else {
            binding.title.text = content.arTitle
        }
    }

    private fun setArticle(content: LibraryContent) {
        val headerColor = "#204167"
        val textColor = "#204167"
        log(content.arDescription.toString())

        htmlText = if (viewModel.sharedPreferences.getString(LANGUAGE) == "en") {
            content.enDescription.toString()
        } else {
            log("ar")
            content.arDescription.toString()
        }
        val formattedHtml = htmlText.replace("\$headerColor", headerColor)
        val spannedText = HtmlCompat.fromHtml(formattedHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.article.text = spannedText

    }

    private fun setupClickListener() {

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

       binding.play.setOnClickListener {
           if(textToSpeech.textToSpeech.isSpeaking){
               textToSpeech.textToSpeech.stop()
               binding.play.setImageResource(R.drawable.icon_stop_sound)
           }else{
               textToSpeech.speakText(htmlText)
               binding.play.setImageResource(R.drawable.icon_play_sound)

           }
       }


        binding.share.setOnClickListener {
            if (viewModel.sharedPreferences.getBoolean(HAS_PARTNER)) {
                val fragment = ChooseSupporterFragment()
                fragment.initOnConfirmButtonClicked(this)
                fragment.show(childFragmentManager, ChooseSupporterFragment::class.java.name)
            } else {
                showToast(getString(R.string.no_supporters_text))
            }
        }

        binding.suggest.setOnClickListener {
            displaySuggestedContent(this, getString(R.string.suggest_other_articles), ARTICLE)
        }

    }

    fun displaySuggestedContent(
        onItemLibraryContentClicked: OnItemLibraryContentClicked,
        title: String,
        type: String
    ) {

        val suggestedContentFragment =
            SuggestedContentFragment()
                .setType(type)
                .setOnItemLibraryContent(onItemLibraryContentClicked)
                .setTitle(title)
        suggestedContentFragment.show(
            childFragmentManager,
            suggestedContentFragment::class.java.name
        )
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

    override fun onInit(p0: Int) {

    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.release()
    }

    override fun onItemClicked(type: String, index: Int, content: String) {
        viewModel.setCurrentContentIndex(index)
        initializeView()

    }



}