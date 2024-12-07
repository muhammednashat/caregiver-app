package mnshat.dev.myproject.users.patient.calender.presentaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.databinding.FragmentCreateOwnActivityBinding


@AndroidEntryPoint
class CreateOwnActivityFragment : BaseFragment() {

    private lateinit var binding: FragmentCreateOwnActivityBinding
    private val viewModel: CalenderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateOwnActivityBinding.inflate(inflater, container, false)
        setListener()
        return binding.root

    }

    private fun setListener() {
//        binding.change.setOnClickListener{
//            ChooseDayFragment().show(childFragmentManager, ChooseDayFragment::class.java.name)
//        }
    }


}