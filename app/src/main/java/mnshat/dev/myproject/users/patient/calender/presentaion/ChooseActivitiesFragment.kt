package mnshat.dev.myproject.users.patient.calender.presentaion

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
import mnshat.dev.myproject.users.patient.calender.domain.entity.DayEntity
import java.util.Date


@AndroidEntryPoint
class ChooseActivitiesFragment : BaseFragment() {

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
        binding.addButton.setOnClickListener{

            findNavController().navigate(R.id.action_chooseActivitiesFragment_to_createOwnActivityFragment)
        }
        binding.button.setOnClickListener{
            val pickedDate = viewModel.getPickedDate()
            val dayEntity = DayEntity(day = pickedDate.calendar.time.toString())
            viewModel.createDayPlan(dayEntity,adapter.getChosenActivities().toList())

        }
    }

    private fun setUpRecyclerView() {
       adapter =CalenderActivitiesAdapter(
               viewModel.getCalenderActivities(requireActivity())
               ,ActivitiesListener {
                 if (it.isNotEmpty()){
                     binding.button.alpha = 1.0f
                 }else{
                     binding.button.alpha = 0.0f
                 }
              })

        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }


    private fun showDoneDialog() {
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

}