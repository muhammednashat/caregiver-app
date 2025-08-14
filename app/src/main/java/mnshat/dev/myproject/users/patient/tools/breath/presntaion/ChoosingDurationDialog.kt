package mnshat.dev.myproject.users.patient.tools.breath.presntaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import mnshat.dev.myproject.databinding.ChoosingDurationDialogBinding
import mnshat.dev.myproject.users.patient.tools.breath.data.Duration

class ChoosingDurationDialog : DialogFragment(), MinutesListener {

    private  val viewModel : BreathViewModel by activityViewModels()
    private lateinit var binding : ChoosingDurationDialogBinding
    private lateinit var minutesAdapter: MinutesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = ChoosingDurationDialogBinding.inflate(inflater, container, false)
        init()
        setUpListeners()
        return  binding.root
    }

    override fun onStart() {
        super.onStart()
       dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val params = dialog?.window?.attributes
        params?.horizontalMargin = 0.05f // 5% margin on each side
        dialog?.window?.attributes = params
    }

    private fun setUpListeners() {
        binding.icBack.setOnClickListener {
            dismiss()
        }

        binding.button.setOnClickListener {
            dismiss()
        }
    }
    private fun init() {
        val list = viewModel.listOfDurations(requireActivity())

        minutesAdapter = MinutesAdapter(list, this)
        binding.recycler.adapter = minutesAdapter
        binding.recycler.layoutManager = GridLayoutManager(
            requireActivity(),
            3,
            GridLayoutManager.VERTICAL,
            false
        )
    }

    override fun onItemClicked(duration: Duration) {
          binding.imageView.setImageResource(duration.image)
         binding.button.isEnabled = true

    }
}