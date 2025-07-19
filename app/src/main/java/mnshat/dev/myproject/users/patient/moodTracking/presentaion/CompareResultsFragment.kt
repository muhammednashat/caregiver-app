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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.DialogProgressMoodBinding
import mnshat.dev.myproject.databinding.FragmentCompareResultsBinding
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.users.patient.main.presentaion.UserScreensActivity
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.viewmodels.MoodTrackingViewModel

@AndroidEntryPoint
class CompareResultsFragment : BaseFragment() {

    private lateinit var binding: FragmentCompareResultsBinding
    private val viewModel: MoodTrackingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCompareResultsBinding.inflate(inflater, container, false)
        setUpListeners()
        setUpUi(viewModel.currentDay())

//        viewModel.getNextDay(1)
        viewModel.getNextDay(viewModel.currentDay().status?.day!!+1)
        viewModel.storeDayMoodTrackingRemotely()
        return  binding.root
    }

    private fun setUpListeners() {
        binding.btnNext.setOnClickListener {
            showDialog()
        }
        binding.icBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun setUpUi(currentDay: CurrentDay) {

    val preMood = viewModel.getEmojisStatus(requireContext())[currentDay.status?.preMoodIndex!!]
    val postMood = viewModel.getEmojisStatus(requireContext())[viewModel.getPostMoodIndex()]

    binding.moodBefore.text = preMood.name
    binding.imageBefore.setImageResource(preMood.emoji)
    binding.containerBefore.setBackgroundColor(Color.parseColor(preMood.backgroundColor))
    binding.containerAfter.setBackgroundColor(Color.parseColor(postMood.backgroundColor))
    binding.moodAfter.text = postMood.name
    binding.imageAfter.setImageResource(postMood.emoji)

    }

    private fun showDialog() {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogProgressMoodBinding.inflate(layoutInflater)
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

        dialogBinding.btnNext.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(requireContext(), UserScreensActivity::class.java))
            activity?.finish()
        }

        dialog.show()
    }

}