package mnshat.dev.myproject.commonFeatures.posts

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentAudioBinding
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.loadImage

class PlayAudioFragment : BasePatientFragment<FragmentAudioBinding>() {

    override fun getLayout() = R.layout.fragment_audio
    private lateinit var player: ExoPlayer
    private var description: String = ""
    private lateinit var handler: Handler
    private var runnable: Runnable? = null

    override fun initializeViews() {
        super.initializeViews()
        binding.share.visibility = View.GONE
        binding.suggest.visibility = View.GONE
        val libraryContent = PlayAudioFragmentArgs.fromBundle(requireArguments()).libraryContent
        displayContent(libraryContent)

    }


    override fun setupClickListener() {
        super.setupClickListener()

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.playButton.setOnClickListener {
            onPlayClicked()
        }

        binding.replay.setOnClickListener {
            player.seekTo(player.currentPosition - 10000)
        }

        binding.forward.setOnClickListener {
            player.seekTo(player.currentPosition + 10000)
        }


        binding.volumeOff.setOnClickListener {
            player.volume = if (player.volume == 0f) {
                binding.volumeOff.setImageResource(R.drawable.ic_volume_up)
                1f
            } else {
                binding.volumeOff.setImageResource(R.drawable.ic_volume_off)

                0f
            }

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


    private fun onPlayClicked() {
        if (player.isPlaying) {
            binding.playButton.setImageResource(R.drawable.ic_play_circle2)
            player.pause()
        } else {
            binding.playButton.setImageResource(R.drawable.ic_pause_circle)
            player.play()
        }
    }

    private fun displayContent(content: LibraryContent) {
        loadImage(requireActivity(), content.imageURL, binding.imageView6)
        loadImage(requireActivity(), content.imageURL, binding.image2)
        initializeExoPlayer(content.audioURL!!)
        setTitle(content)
        setDescription(content)
        binding.date.text = content.date
    }

    private fun initializeExoPlayer(uriString: String) {
        player = ExoPlayer.Builder(requireContext()).build()
        val mediaItem = MediaItem.fromUri(Uri.parse(uriString))
        player.setMediaItem(mediaItem)
        player.prepare()
        setSeekBar()
    }


    private fun setSeekBar() {
        handler = Handler(Looper.getMainLooper())
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    player.seekTo(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                player.pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                player.play()
            }
        })

        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    startUpdatingSeekBar()
                } else {
                    stopUpdatingSeekBar()
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                // Set the SeekBar maximum when the media is ready
                if (state == Player.STATE_READY) {
                    binding.seekBar.max = player.duration.toInt()
                }
            }
        })
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

    private fun startUpdatingSeekBar() {
        runnable = Runnable {
            binding.seekBar.progress = player.currentPosition.toInt()
            handler.postDelayed(runnable!!, 1000)
        }
        handler.post(runnable!!)
    }

    private fun stopUpdatingSeekBar() {
//        handler.removeCallbacks(runnable!!)
    }

    override fun onStop() {
        super.onStop()
        player.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
        stopUpdatingSeekBar()
    }

}