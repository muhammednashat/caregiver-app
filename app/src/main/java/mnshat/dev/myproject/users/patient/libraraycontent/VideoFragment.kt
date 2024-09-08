package mnshat.dev.myproject.users.patient.libraraycontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentArticleBinding
import mnshat.dev.myproject.databinding.FragmentVideoBinding
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.COMMON_CONTENT
import mnshat.dev.myproject.util.LANGUAGE

class VideoFragment : BaseLibraryFragment<FragmentVideoBinding>() {

    override fun getLayout() = R.layout.fragment_video
    private var description: String = ""
    override fun initializeViews() {
        super.initializeViews()
        initializeView()
    }

    override fun setupClickListener() {
        super.setupClickListener()
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.iconShowDescription.setOnClickListener {
            hideLabelDescription(true)
            hideIconCloseDescription(false)
            hideTextDescription(false)
        }
        binding.iconHideDescription.setOnClickListener {
            hideLabelDescription(false)
            hideIconCloseDescription(true)
            hideTextDescription(true)
        }
    }

    private fun initializeView() {

        val index = viewModel.getCurrentContentIndex()
        val libraryContentList =
            when (viewModel.getCurrentContent()) {
                COMMON_CONTENT -> viewModel.mLibraryContentMostCommon
                else -> viewModel.mLibraryContentCustomized
            }
        val content = libraryContentList[index]
        setTitle(content)
        setDescription(content)
        binding.date.text = content.date

    }

    private fun hideLabelDescription(isHide: Boolean) {
        if (isHide) {
            binding.labelDescription.visibility = View.GONE
            binding.iconShowDescription.visibility = View.GONE
        } else {
            binding.labelDescription.visibility = View.VISIBLE
            binding.iconShowDescription.visibility = View.VISIBLE
        }
    }

    private fun hideIconCloseDescription(isHide: Boolean) {
        if (isHide) {
            binding.iconHideDescription.visibility = View.GONE
        } else {
            binding.iconHideDescription.visibility = View.VISIBLE
        }
    }

    private fun hideTextDescription(isHide: Boolean) {
        if (isHide) {
            binding.textDescription.text = ""
        } else {
            binding.textDescription.text = description
        }
    }

    private fun setTitle(content: LibraryContent) {
        if (sharedPreferences.getString(LANGUAGE) == "en") {
            binding.title.text = content.enTitle
        } else {
            binding.title.text = content.arTitle
        }
    }

    private fun setDescription(content: LibraryContent) {
        if (sharedPreferences.getString(LANGUAGE) == "en") {
            description = content.enDescription?.repeat(200)!!

        } else {
            description = content.arTitle?.repeat(200)!!

        }
    }
}