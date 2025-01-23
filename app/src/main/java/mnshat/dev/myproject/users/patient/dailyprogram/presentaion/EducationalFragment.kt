package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogPreMoodSelectionBinding
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.Task
import mnshat.dev.myproject.users.patient.moodTracking.presentaion.MoodTrackingActivity
import mnshat.dev.myproject.util.RELIGION
import mnshat.dev.myproject.util.log

@AndroidEntryPoint
class EducationalFragment : BaseDailyProgramFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutTaskBinding.inflate(inflater, container, false)
        setupClickListener()
        showProgressDialog()
        viewModel.get()
        observeViewModel()
        log("EducationalFragment onCreateView")
        binding.text2.text = Html.fromHtml(
          "تقبلك للمشاعر السليبة كالضيق أو الحزن أو الخوف وهي مشاعر نخوضها في هذه الدنيا لا يدل على ضعف منك أو قصور في شخصيتك، فالأنبياء عليهم السلام وهم أشرف البشر مرّوا بنفس المشاعر التي مررت بها.<br>" +
                  "﴿وَلَقَدْ نَعْلَمُ أَنَّكَ يَضِيقُ صَدْرُكَ بِمَا يَقُولُونَ﴾ و ﴿وَلَا تَحْزَنْ عَلَيْهِمْ وَلَا تَكُنْ فِي ضَيْقٍ مِمَّا يَمْكُرُونَ﴾ عن النبي محمد صلى الله عليه وسلم.<br>" +
                  "﴿وَيَضِيقُ صَدْرِي وَلَا يَنْطَلِقُ لِسَانِي فَأَرْسِلْ إِلَى هَارُونَ﴾ و ﴿فَنَجَّيْنَاكَ مِنَ الْغَمِّ ﴾ عن النبي موسى عليه السلام.<br>" +
                  "﴿قَالَ إِنِّي لَيَحْزُنُنِي أَنْ تَذْهَبُوا بِهِ وَأَخَافُ أَنْ يَأْكُلَهُ الذِّئْبُ وَأَنْتُمْ عَنْهُ غَافِلُونَ﴾ و ﴿وَتَوَلَّى عَنْهُمْ وَقَالَ يَا أَسَفَى عَلَى يُوسُفَ وَابْيَضَّتْ عَيْنَاهُ مِنَ الْحُزْنِ فَهُوَ كَظِيمٌ﴾ و ﴿قَالَ إِنَّمَا أَشْكُو بَثِّي وَحُزْنِي إِلَى اللَّهِ وَأَعْلَمُ مِنَ اللَّهِ مَا لَا تَعْلَمُونَ﴾ عن النبي يعقوب عليه السلام.<br>" +
                  "﴿وَلَمَّا أَنْ جَاءَتْ رُسُلُنَا لُوطًا سِيءَ بِهِمْ وَضَاقَ بِهِمْ ذَرْعًا وَقَالُوا لَا تَخَفْ وَلَا تَحْزَنْ إِنَّا مُنَجُّوكَ وَأَهْلَكَ إِلَّا امْرَأَتَكَ كَانَتْ مِنَ الْغَابِرِينَ﴾. ﴿فَنَادَاهَا مِنْ تَحْتِهَا أَلَّا تَحْزَنِي قَدْ جَعَلَ رَبُّكِ تَحْتَكِ سَرِيًّا﴾ عن مريم عليها السلام.<br>" +
                  "وفي الأخير هناك آية عليك تأملها إذ يقول الله عزوجل مخاطبا عباده المؤمنين في يوم القيامة ﴿يَا عِبَادِ لَا خَوْفٌ عَلَيْكُمُ الْيَوْمَ وَلَا أَنْتُمْ تَحْزَنُونَ﴾ ويقول المؤمنون إذا دخلوا الجنة ﴿الْحَمْدُ لِلَّهِ الَّذِي أَذْهَبَ عَنَّا الْحَزَنَ إِنَّ رَبَّنَا لَغَفُورٌ شَكُورٌ﴾."
        )
//
        return  binding.root
    }

    override fun onStart() {
       log("EducationalFragment onStart")

        super.onStart()

    }

    fun observeViewModel(){
       viewModel.currentDay.observe(viewLifecycleOwner){
          it?.let{
              log("EducationalFragment observeViewModel ${it.status}")
               dismissProgressDialog()
              viewModel.status = it.status!!
               isPreChecked()
               initializeViews()
           }
       }
    }

     fun initializeViews() {
        binding.btnPrevious.visibility = View.GONE
        viewModel. currentDay.value.let {
            viewModel.listOfTasks = it?.dayTask?.educational as List<Task>
            if ( viewModel.listOfTasks.size == 1) binding.btnRecommend.visibility = View.GONE
            getTaskFromList(viewModel.status.currentIndexEducational!!, 2)
            changeColorStatus()
        }

        hideSpiritualIcon(binding.constraintTask2, binding.line1)
    }

    private fun isPreChecked() {
        if (viewModel.status.preChecked == false){
            showDailyProgram()
        }
    }



    private fun showDailyProgram() {
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
            startActivity(Intent(requireContext(), MoodTrackingActivity::class.java))
            activity?.finish()
        }

        dialog.show()
    }


   private  fun setupClickListener() {
        binding.btnNext.setOnClickListener {
            updateStatus()
        }

        binding.icPause.setOnClickListener {
            showTemporallyDialog(
                getString(R.string.do_you_want_to_pause_this_task_temporarily),
                getString(R.string.you_can_exit_this_task_and_come_back_again_whatever_you_want),
                R.drawable.ic_timer,
                getString(R.string.pause_task_temporarily)
            ) {
                requireActivity().finish()
            }

        }

        binding.btnRecommend.setOnClickListener {
            val currentIndex = getNextTask(viewModel.status.currentIndexEducational!!, 1)
            viewModel.status.currentIndexEducational = currentIndex
            viewModel.updateCurrentTaskLocally()
        }
    }

    private fun changeColorStatus() {
        if (viewModel.status.educational == 1) binding.line1.setBackgroundColor(Color.parseColor("#6db7d3"))
        changeColorOfTaskImage(2, binding.constraintTask1, binding.imageTask1)
        changeColorOfTaskImage(viewModel.status.spiritual, binding.constraintTask2, binding.imageTask2)
        changeColorOfTaskImage(viewModel.status.behavioral, binding.constraintTask3, binding.imageTask3)

    }

    private fun updateStatus() {
        if (viewModel.status.educational != 1){
            updateStatusData()
        }
        navigateToNextTask()
    }

    private fun updateStatusData() {
        viewModel.status.educational = 1
        updateCompletionRate()
        showToast(getString(R.string.the_first_task_was_completed_successfully))
   }



    private fun navigateToNextTask() {
        if (viewModel.sharedPreferences.getBoolean(RELIGION)) {
            findNavController().navigate(R.id.action_educationalFragment_to_spiritualFragment)
        } else {
            findNavController().navigate(R.id.action_educationFragment_to_behaviorActivationFragment)
        }
    }

    override fun onStop() {
        log("EducationalFragment onStop")
        super.onStop()
        player?.pause()
        if (viewModel._isSyncNeeded.value == true){
            viewModel.updateCurrentTaskRemotely()
            viewModel._isSyncNeeded.value = false
        }
    }



}

