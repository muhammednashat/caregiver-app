package mnshat.dev.myproject.users.patient.calender.presentaion

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.DialogCalenderBinding
import mnshat.dev.myproject.databinding.FragmentChooseActiviesBinding
import mnshat.dev.myproject.users.patient.calender.domain.entity.CalenderActivity


@AndroidEntryPoint
class ChooseActivitiesFragment : BaseFragment() , OnActivityClickListener{

    private val viewModel: CalenderViewModel by viewModels()
    private  lateinit var adapter: CalenderActivitiesAdapter
    private lateinit var binding: FragmentChooseActiviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseActiviesBinding.inflate(inflater,container, false)
        setUpRecyclerView()
        setListener()
        return binding.root
    }

    private fun setListener(){
  

        binding.back.setOnClickListener{
            findNavController().popBackStack()
        }


        binding.button.setOnClickListener{
            val dayEntity = viewModel.getDayEntity()
            val activities = adapter.getChosenActivities().toList()
            val tasks = viewModel.toTaskEntities(activities,dayEntity.day)
            viewModel.createDayPlan(dayEntity,tasks)
            showDoneDialog()
        }

    }

    private fun setUpRecyclerView() {
        adapter =CalenderActivitiesAdapter(
        viewModel.getCalenderActivities(requireActivity()),this )
        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }


    private fun showDoneDialog() {
//        showTemporallyDialog(
//            getString(R.string.do_you_want_to_pause_this_task_temporarily),
//            getString(R.string.you_can_exit_this_task_and_come_back_again_whatever_you_want),
//            R.drawable.ic_timer,
//            getString(R.string.pause_task_temporarily)
//        ) {
////            requireActivity().finish()
//        }


        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogCalenderBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)

        val window = dialog.window
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.button.setOnClickListener {
            dialog.dismiss()
            findNavController().popBackStack()
        }

        dialog.show()
    }











    override fun onAddActivity(activities: Set<CalenderActivity>) {






            if (activities.isNotEmpty()){
                binding.button.alpha = 1.0f
            }else{
                binding.button.alpha = 0.0f
            }




    }

    override fun createCustomActivity() {

        val activities = adapter.getChosenActivities().toList() ?: emptyList()
        viewModel.setChosenActivities(activities)
        val action = ChooseActivitiesFragmentDirections
            .actionChooseActivitiesFragmentToCreateOwnActivityFragment("creating")
        findNavController().navigate(action)





    }

}