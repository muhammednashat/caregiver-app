package mnshat.dev.myproject.users.patient.tools.breath.presntaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import mnshat.dev.myproject.databinding.ChoosingSoundDialogBinding
import kotlin.getValue

class ChoosingSoundDialog: DialogFragment() {

    private  val viewModel : BreathViewModel by activityViewModels()

    private lateinit var binding : ChoosingSoundDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ChoosingSoundDialogBinding.inflate(inflater, container, false)
        return  binding.root
    }

}