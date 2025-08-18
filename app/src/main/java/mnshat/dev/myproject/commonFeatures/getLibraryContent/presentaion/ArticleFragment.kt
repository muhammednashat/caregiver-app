package mnshat.dev.myproject.commonFeatures.getLibraryContent.presentaion

import android.content.res.ColorStateList
import android.graphics.Color
import android.speech.tts.TextToSpeech
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.commonFeatures.posts.presentation.ChooseSupporterFragment
import mnshat.dev.myproject.databinding.FragmentArticleBinding
import mnshat.dev.myproject.commonFeatures.posts.OnSendButtonClicked
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.LIBRARY
import mnshat.dev.myproject.util.TextToSpeechUtil
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log


class ArticleFragment : BaseLibraryFragment<FragmentArticleBinding>(), OnSendButtonClicked, TextToSpeech.OnInitListener {


    override fun getLayout() = R.layout.fragment_article
    private lateinit var htmlText: String
    private lateinit var textToSpeech: TextToSpeechUtil
    override fun initializeViews() {
        super.initializeViews()
        initializeView()
    }


    private fun initializeView() {
        textToSpeech =  TextToSpeechUtil(TextToSpeech(requireActivity(), this))
        val content = viewModel.getContent()
//        content.arText = "#0081bf"
        binding.container.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(content.backgroundColor)); // Change to any color
        setArticle(content)
        setTitles(content)
        loadImage(requireActivity(), content.imageURL, binding.imageView)
        loadImage(requireActivity(), content.imageURL, binding.imageView2)
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
        val headerColor = "#204167"
        val textColor = "#204167"
        log(content.arDescription.toString())

        htmlText = if (sharedPreferences.getString(LANGUAGE) == "en") {
            content.enDescription.toString()
        } else {
            log("ar")
            content.arDescription.toString()
        }
        val formattedHtml = htmlText.replace("\$headerColor", headerColor)
        val spannedText = HtmlCompat.fromHtml(formattedHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.article.text = spannedText

    }

    override fun setupClickListener() {
        super.setupClickListener()

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
            if (sharedPreferences.getBoolean(HAS_PARTNER)) {
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

    override fun onInit(p0: Int) {
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.release()
    }

}