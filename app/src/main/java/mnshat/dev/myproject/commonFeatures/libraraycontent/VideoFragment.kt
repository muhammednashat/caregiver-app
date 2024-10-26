package mnshat.dev.myproject.commonFeatures.libraraycontent

import android.net.Uri
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.firebase.firestore.FirebaseFirestore
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.CommonContentLibraryAdapter
import mnshat.dev.myproject.commonFeatures.sharingcontent.ChooseSupporterFragment
import mnshat.dev.myproject.databinding.FragmentVideoBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.interfaces.OnSendButtonClicked
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.model.SharingContent
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.LIBRARY
import mnshat.dev.myproject.util.VIDEO
import mnshat.dev.myproject.util.log

class VideoFragment : BaseLibraryFragment<FragmentVideoBinding>(), OnSendButtonClicked {

    override fun getLayout() = R.layout.fragment_video
  private lateinit var player:ExoPlayer
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
        binding.share.setOnClickListener {
            val fragment = ChooseSupporterFragment()
            fragment.initOnConfirmButtonClicked(this)
            fragment.show(childFragmentManager, ChooseSupporterFragment::class.java.name)
        }

        binding.showAll.setOnClickListener {

            navigateToCustomizedContent(viewModel.getContentsBasedType(VIDEO).toTypedArray(),getString(R.string.more_similar_videos))

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

        displayContent(viewModel.getContent())

        initRecyclerView(viewModel.getContentsBasedType(VIDEO))

    }
    private fun displayContent(content: LibraryContent) {

        playVideoAudio(Uri.parse(content.videoURL))
        setTitle(content)
        setDescription(content)
        binding.date.text = content.date
    }

    private fun initRecyclerView(libraryContents: List<LibraryContent>) {

    binding.recycler.apply {
        layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = CommonContentLibraryAdapter(libraryContents,requireContext(),sharedPreferences,this@VideoFragment)
    }

    }

    private fun navigateToCustomizedContent(contents: Array<LibraryContent>, textTitle: String) {

        val action = VideoFragmentDirections
            .actionVideoFragmentToCustomizedContentFragment(contents,textTitle)
        findNavController().navigate(action)


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

    override fun onItemClicked(type: String, index: Int, content: String) {

        log("video")
        updateCurrentContent(content)
        updateCurrentIndex(index)
        displayContent(viewModel.getContent())

    }

    override fun onStop() {
        super.onStop()
        player.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }


    fun getSharingContent(list: MutableList<String>) =
        SharingContent(
            type =  LIBRARY,
            libraryContent = viewModel.getContent(),
            supporters = list
        )


    override fun onSendClicked(list: MutableList<String>) {
        showProgressDialog()
        viewModel.shareContent(getSharingContent(list)){
            if (it == null){
                showToast("done")
            }else{
                showToast(it)
            }
            dismissProgressDialog()
        }

    }

}