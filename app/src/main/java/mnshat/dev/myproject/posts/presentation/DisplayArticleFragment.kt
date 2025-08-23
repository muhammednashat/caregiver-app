package mnshat.dev.myproject.posts.presentation

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
import mnshat.dev.myproject.databinding.FragmentArticleBinding
import mnshat.dev.myproject.databinding.FragmentDisplayArticleBinding
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.getLibraryContent.presentaion.LibraryViewModel
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.TextToSpeechUtil
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log

class DisplayArticleFragment : BaseFragment(), TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentDisplayArticleBinding
    private val viewModel: LibraryViewModel by activityViewModels()
    private lateinit var htmlText: String
    private lateinit var textToSpeech: TextToSpeechUtil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentDisplayArticleBinding.inflate(inflater, container, false)
        initializeView()
        setupClickListener()
        return binding.root


    }


    private fun initializeView() {
        textToSpeech =  TextToSpeechUtil(TextToSpeech(requireActivity(), this))

        val libraryContent =
            DisplayArticleFragmentArgs.fromBundle(requireArguments()).libraryContent as LibraryContent
        setArticle(libraryContent)
        setTitles(libraryContent)
        loadImage(requireActivity(), libraryContent.imageURL, binding.imageView)
        loadImage(requireActivity(), libraryContent.imageURL, binding.imageView2)

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

    }
    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.release()
    }
    override fun onInit(p0: Int) {

    }

}