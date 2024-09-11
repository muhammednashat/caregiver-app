package mnshat.dev.myproject.users.patient.libraraycontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentAudioBinding
import mnshat.dev.myproject.databinding.FragmentVideoBinding

class AudioFragment  : BaseLibraryFragment<FragmentAudioBinding>() {

    override fun getLayout() = R.layout.fragment_audio

}