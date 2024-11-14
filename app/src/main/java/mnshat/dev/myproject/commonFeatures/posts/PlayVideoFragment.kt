package mnshat.dev.myproject.commonFeatures.posts

import android.net.Uri
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentVideoBinding
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.LANGUAGE


class PlayVideoFragment  : BasePatientFragment<FragmentVideoBinding>() {

    override fun getLayout() = R.layout.fragment_video
    private lateinit var player: ExoPlayer
    private var description: String = ""

    override fun initializeViews() {
        super.initializeViews()
        binding.constraintLayout.visibility = View.GONE
        binding.share.visibility = View.GONE
        val libraryContent = PlayVideoFragmentArgs.fromBundle(requireArguments()).libraryContent
        displayContent(libraryContent)
    }
    private fun displayContent(content: LibraryContent) {

        playVideoAudio(Uri.parse(content.videoURL))
        setTitle(content)
        setDescription(content)
        binding.date.text = content.date
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

    private fun playVideoAudio(uri: Uri) {
        player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
            binding.exoPlayer.player = exoPlayer
            val mediaItem = MediaItem.fromUri(uri)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()

            dismissProgressDialog()
//            exoPlayer.playWhenReady = true
        }

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
    override fun onStop() {
        super.onStop()
        player.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

}