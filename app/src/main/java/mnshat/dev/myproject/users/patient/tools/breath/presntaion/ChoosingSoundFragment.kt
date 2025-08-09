package mnshat.dev.myproject.users.patient.tools.breath.presntaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentChooseSuondBinding
import mnshat.dev.myproject.databinding.FragmentChoosingBreathDurationBinding
import kotlin.getValue

@AndroidEntryPoint

class ChoosingSoundFragment : Fragment() {

    private  val viewModel : BreathViewModel by activityViewModels()
    private  lateinit var  binding : FragmentChooseSuondBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseSuondBinding.inflate(inflater, container, false)
        return  binding.root

    }

}