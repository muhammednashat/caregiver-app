package mnshat.dev.myproject.users.patient.dailyprogram

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentSuggestedChallengesBinding
import mnshat.dev.myproject.factories.DailyProgramViewModelFactory
import mnshat.dev.myproject.model.Task
import mnshat.dev.myproject.model.TestD
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
            val newListOfTasks = mutableListOf<TestD>()
            for (index in tasks.indices) {
                if (index != currentIndex) newListOfTasks.add(TestD(index, tasks[index].image!!))
            }
            setUpImageTask1(newListOfTasks)
            setUpImageTask2(newListOfTasks)
            setUpImageTask3(newListOfTasks)
            setUpImageTask4(newListOfTasks)

        }
    }

    private fun setUpImageTask1(list: List<TestD>) {
        Glide.with(binding.root)
            .load(list[0].image)
            .centerCrop()
            .into(binding.imageTask1)
        binding.imageTask1.setOnClickListener {
            onItemClick(list[0].index)
        }

    }
    private fun setUpImageTask2(list: List<TestD>) {
        Glide.with(binding.root)
            .load(list[1].image)
            .centerCrop()
            .into(binding.imageTask2)
        binding.imageTask2.setOnClickListener {
            onItemClick(list[1].index)
        }

    }
    private fun setUpImageTask3(list: List<TestD>) {
        Glide.with(binding.root)
            .load(list[2].image)
            .centerCrop()
            .into(binding.imageTask3)
        binding.imageTask3.setOnClickListener {
            onItemClick(list[2].index)
        }

    }
    private fun setUpImageTask4(list: List<TestD>) {
        Glide.with(binding.root)
            .load(list[3].image)
            .centerCrop()
            .into(binding.imageTask4)
        binding.imageTask4.setOnClickListener {
            onItemClick(list[3].index)
        }

    }

    override fun setupClickListener() {
        super.setupClickListener()
      binding.close.setOnClickListener{
          dismiss()
      }
    }
    private fun initViewModel() {
        val factory = DailyProgramViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[DailyProgramViewModel::class.java]
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