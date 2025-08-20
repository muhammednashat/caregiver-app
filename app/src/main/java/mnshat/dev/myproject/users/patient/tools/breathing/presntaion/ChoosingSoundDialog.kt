package mnshat.dev.myproject.users.patient.tools.breathing.presntaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import mnshat.dev.myproject.databinding.ChoosingSoundDialogBinding
import mnshat.dev.myproject.users.patient.tools.breathing.model.Sound
import kotlin.getValue

class ChoosingSoundDialog: DialogFragment(), OnItemSoundClicked {

    private  val viewModel : BreathViewModel by activityViewModels()

    private lateinit var binding : ChoosingSoundDialogBinding
    private lateinit var soundsAdapter: SoundsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ChoosingSoundDialogBinding.inflate(inflater, container, false)
        setSoundsRecycler(viewModel.listOfSounds(requireActivity()))
        setUpListeners()
        return  binding.root
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val params = dialog?.window?.attributes
        params?.horizontalMargin = 0.05f
        dialog?.window?.attributes = params
    }


    private fun setUpListeners() {
        binding.icBack.setOnClickListener {
           dismiss()
        }
        binding.button.setOnClickListener {
            viewModel.setChangeSound(true)
            dismiss()
        }
    }
    private fun setSoundsRecycler(sounds: List<Sound>) {
        soundsAdapter = SoundsAdapter(sounds, requireActivity(), this)
        binding.recycler.adapter = soundsAdapter
        binding.recycler.layoutManager = GridLayoutManager(context, 2)
    }

    override fun onItemClicked(soundId: Int?) {
        binding.button.isEnabled = true
        viewModel.soundId = soundId!!
    }

}