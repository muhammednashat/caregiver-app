package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import android.os.Bundle
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment2
import mnshat.dev.myproject.databinding.FragmentSuggestedChallengesBinding
import mnshat.dev.myproject.model.TestD
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.Task
import mnshat.dev.myproject.util.ENGLISH_KEY
import kotlin.properties.Delegates

class SuggestedChallengesFragment() :
    BaseBottomSheetDialogFragment2<FragmentSuggestedChallengesBinding>() {
    private lateinit var adapter: SuggestedChallengesAdapter

    interface OnTaskItemClickListener {
        fun onTaskItemClicked(position:Int)
    }


    private var tasks: List<Task> = emptyList()
    private var currentIndex by Delegates.notNull<Int>()
    private  var lang = ""
    private var _itemClickListener: OnTaskItemClickListener? = null
    override fun getLayout() = R.layout.fragment_suggested_challenges


    override fun initializeViews() {
        loadTasksFromArguments()
        changeButtonBackColorBasedLang()
    }


    private fun loadTasksFromArguments() {
        arguments?.let {
            tasks = it.getParcelableArrayList(ARG_TASKS) ?: emptyList()
            currentIndex = it.getInt(ARG_CURRENT_INDEX)
            lang = it.getString(ARG_LANG)!!
            adapter = SuggestedChallengesAdapter(tasks, currentIndex,lang)
            binding.recycler.adapter = adapter


            val newListOfTasks = mutableListOf<TestD>()
            for (index in tasks.indices) {
                if (index != currentIndex) newListOfTasks.add(TestD(index, tasks[index].image!!))
            }

        }
    }

    private fun setUpImageTask4(list: List<TestD>) {
//        Glide.with(binding.root)
//            .load(list[3].image)
//            .centerCrop()
//            .into(binding.imageTask4)
//        binding.imageTask4.setOnClickListener {
//            onItemClick(list[3].index)
//        }

    }

    override fun setupClickListener() {
        super.setupClickListener()

        binding.buttonConfirm.setOnClickListener {
            onItemClick(adapter.getSelectedPosition())
            dismiss()
        }



      binding.close.setOnClickListener{
          dismiss()
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
        private const val  ARG_LANG = "lang"
        fun newInstance(itemClickListener: OnTaskItemClickListener, currentIndex:Int, tasks: List<Task>,lang:String) =
            SuggestedChallengesFragment().apply {
                _itemClickListener=itemClickListener
                arguments = Bundle().apply {
                    putInt(ARG_CURRENT_INDEX,currentIndex)
                    putString(ARG_LANG,lang)
                    putParcelableArrayList(ARG_TASKS, ArrayList(tasks))
                }
             }

    }

}