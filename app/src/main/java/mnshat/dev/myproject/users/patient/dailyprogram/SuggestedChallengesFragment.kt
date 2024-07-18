package mnshat.dev.myproject.users.patient.dailyprogram

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentSuggestedChallengesBinding
import mnshat.dev.myproject.factories.DailyProgramViewModelFactory
import mnshat.dev.myproject.model.Task
import mnshat.dev.myproject.util.ENGLISH_KEY
import kotlin.properties.Delegates

class SuggestedChallengesFragment() :
    BaseBottomSheetDialogFragment<FragmentSuggestedChallengesBinding>() {

    interface OnTaskItemClickListener {
        fun onTaskItemClicked(position:Int)
    }


    private lateinit var viewModel: DailyProgramViewModel
    private var tasks: List<Task> = emptyList()
    private var currentIndex by Delegates.notNull<Int>()
    private var _itemClickListener: OnTaskItemClickListener? = null
    override fun getLayout() = R.layout.fragment_suggested_challenges


    override fun initializeViews() {
        loadTasksFromArguments()
        initViewModel()
        changeButtonBackColorBasedLang()
    }


    private fun loadTasksFromArguments() {
        arguments?.let {
            tasks = it.getParcelableArrayList(ARG_TASKS) ?: emptyList()
            currentIndex = it.getInt(ARG_CURRENT_INDEX)
            val remainingTasks = tasks.filterIndexed { index, _ -> index != currentIndex }
            displayRemainingTasks(remainingTasks)
        }
    }

    private fun displayRemainingTasks(remainingTasks: List<Task>) {
        if (remainingTasks.size >= 4) {
            Glide.with(binding.root)
                .load(remainingTasks[0].image)
                .centerCrop()
                .into(binding.imageTask1)

            Glide.with(binding.root)
                .load(remainingTasks[1].image)
                .centerCrop()
                .into(binding.imageTask2)

            Glide.with(binding.root)
                .load(remainingTasks[2].image)
                .centerCrop()
                .into(binding.imageTask3)

            Glide.with(binding.root)
                .load(remainingTasks[3].image)
                .centerCrop()
                .into(binding.imageTask4)
        }
    }

    private fun initViewModel() {
        val factory = DailyProgramViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[DailyProgramViewModel::class.java]
    }

    override fun setupClickListener() {
        binding.close.setOnClickListener {
            dismiss()
        }
        binding.imageTask1.setOnClickListener {
            onItemClick(0)
        }
        binding.imageTask2.setOnClickListener {
            onItemClick(1)
        }
        binding.imageTask3.setOnClickListener {
            onItemClick(2)
        }
        binding.imageTask4.setOnClickListener {
            onItemClick(3)
        }
    }

    private fun onItemClick(position: Int) {
        _itemClickListener?.onTaskItemClicked(position)
        dismiss()
    }

    private fun changeButtonBackColorBasedLang() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
    }


    companion object {
        private const val ARG_TASKS = "tasks"
        private const val ARG_CURRENT_INDEX ="currentIndex"
        fun newInstance(itemClickListener: OnTaskItemClickListener,currentIndex:Int,tasks: List<Task>) =
            SuggestedChallengesFragment().apply {
                _itemClickListener=itemClickListener
                arguments = Bundle().apply {
                    putInt(ARG_CURRENT_INDEX,currentIndex)
                    putParcelableArrayList(ARG_TASKS, ArrayList(tasks))
                }
             }

    }

}