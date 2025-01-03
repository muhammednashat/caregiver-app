package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentDayTaskBinding


@AndroidEntryPoint
class DayTaskFragment : BaseFragment() {

    private lateinit var binding: FragmentDayTaskBinding
    private val viewModel: DayTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding = FragmentDayTaskBinding.inflate(inflater, container, false)

      viewModel.get()
       return  binding.root
    }

}