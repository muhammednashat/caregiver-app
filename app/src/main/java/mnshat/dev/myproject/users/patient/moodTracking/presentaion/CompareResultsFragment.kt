package mnshat.dev.myproject.users.patient.moodTracking.presentaion

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogPreMoodSelectionBinding
import mnshat.dev.myproject.databinding.FragmentCompareResultsBinding
import mnshat.dev.myproject.users.patient.dailyprogram.DailyProgramActivity

@AndroidEntryPoint
class CompareResultsFragment : BaseFragment() {

    private lateinit var binding: FragmentCompareResultsBinding
    private val viewModel: MoodViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompareResultsBinding.inflate(inflater, container, false)
        binding.btnNext.setOnClickListener {
            showDialog()
        }
        return  binding.root
    }
    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogPreMoodSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)

        val window = dialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.8).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }

        dialogBinding.icClose.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.button.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(requireContext(), DailyProgramActivity::class.java))
            activity?.finish()
        }

        dialog.show()
    }

}