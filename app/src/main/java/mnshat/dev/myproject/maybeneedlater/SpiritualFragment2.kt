package mnshat.dev.myproject.maybeneedlater

import com.google.android.exoplayer2.ExoPlayer
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.users.patient.main.BaseUserFragment

class SpiritualFragment2 : BaseUserFragment<LayoutTaskBinding>() {

    private var player: ExoPlayer? = null
    private val urlVideo =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/videos%2F3%20_Binary%20Search%20-%20Recursive%20implementation(720P_HD).mp4?alt=media&token=78e30d71-1b51-4ff6-9d0b-de3f6365aff1"
    override fun setupClickListener() {

    }

    override fun getLayout() = R.layout.layout_task
    override fun initializeViews() {
    }

//    private fun initializePlayer() {
//        player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
//            binding.playerView.player = exoPlayer
//            val mediaItem = MediaItem.fromUri(Uri.parse(urlVideo))
//            exoPlayer.setMediaItem(mediaItem)
//            exoPlayer.prepare()
//            val lastPosition = sharedPreferences.getLong("last_position", 0L)
//            exoPlayer.seekTo(lastPosition)
//            exoPlayer.playWhenReady = true
//            exoPlayer.addListener(object : Player.Listener {
//                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//                    if (playbackState == Player.STATE_READY) {
//                        sharedPreferences.storeLang("last_position", exoPlayer.currentPosition)
//                    }
//                    if (playWhenReady) {
//                        binding.btnPlayPause .setImageResource(R.drawable.ic_pause)
//                    } else {
//                        binding.btnPlayPause .setImageResource(R.drawable.ic_play2)
//                    }
//                }
//            })
//
//            binding.seekBar.max = exoPlayer.duration.toInt()
//            binding.seekBar.progress = exoPlayer.currentPosition.toInt()
//
//            exoPlayer.addListener(object : Player.Listener {
//                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//                    if (playbackState == Player.STATE_READY) {
//                        binding.seekBar.max = exoPlayer.duration.toInt()
//                    }
//                }
//            })
//
//            exoPlayer.addListener(object : Player.Listener {
//                override fun onIsPlayingChanged(isPlaying: Boolean) {
//                    if (isPlaying) {
//                        binding.seekBar.postDelayed(updateSeekRunnable, 1000)
//                    } else {
//                        binding.seekBar.removeCallbacks(updateSeekRunnable)
//                    }
//                }
//            })
//
//            binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
//                    if (fromUser) {
//                        exoPlayer.seekTo(progress.toLong())
//                    }
//                }
//
//                override fun onStartTrackingTouch(seekBar: SeekBar) {}
//                override fun onStopTrackingTouch(seekBar: SeekBar) {}
//            })
//        }
//
//        binding.btnPlayPause.setOnClickListener {
//            if (player!!.isPlaying) {
//                player!!.pause()
//            } else {
//                player!!.play()
//            }
//        }
//
//        binding.btnVolume.setOnClickListener {
//            val volume = if (player!!.volume == 1f) 0f else 1f
//            player!!.volume = volume
//            binding.btnVolume.setImageResource(
//                if (volume == 0f) R.drawable.ic_volume_off else R.drawable.ic_volume_up
//            )
//        }
//
//        binding.btnFullscreen.setOnClickListener {
//        }
//    }
//
//    private val updateSeekRunnable = object : Runnable {
//        override fun run() {
//            binding.seekBar.progress = player!!.currentPosition.toInt()
//            binding.seekBar.postDelayed(this, 1000)
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }
}
