package mnshat.dev.myproject.features.libraraycontent

import android.media.MediaPlayer
import android.widget.ImageButton
import android.widget.SeekBar
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentAudioBinding

class AudioBottomSheetFragment: BaseBottomSheetDialogFragment<FragmentAudioBinding>() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun getLayout()= R.layout.fragment_audio


    override fun initializeViews() {
        super.initializeViews()
        intadf()
    }
    fun intadf(){
        mediaPlayer = MediaPlayer().apply {
            setDataSource(  "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/audios%2F%D8%B9%D9%84%D9%89%20%D9%82%D8%AF%D8%B1%20%D8%A7%D9%87%D9%84%20%D8%A7%D9%84%D8%B9%D8%B2%D9%85.mp3?alt=media&token=bf6e8bbc-a897-4f04-ae55-5b4922aaa1c2"
            )
            prepareAsync()
            setOnPreparedListener {
                start()
            }
        }
    }
}