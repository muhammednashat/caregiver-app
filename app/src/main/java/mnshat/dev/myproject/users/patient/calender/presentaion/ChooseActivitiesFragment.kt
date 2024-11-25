package mnshat.dev.myproject.users.patient.calender.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentChooseActiviesBinding
import javax.inject.Inject


@AndroidEntryPoint
class ChooseActivitiesFragment : BaseBottomSheetDialogFragment() {

    private val viewModel: CalenderViewModel by viewModels()
    private  lateinit var adapter: CalenderActivitiesAdapter
    private lateinit var binding: FragmentChooseActiviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseActiviesBinding.inflate(inflater,container, false)
        setUpRecyclerView()
        return binding.root

    }

    private fun setUpRecyclerView() {
       adapter = CalenderActivitiesAdapter(viewModel.getCalenderActivities(requireActivity()))
        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }


}