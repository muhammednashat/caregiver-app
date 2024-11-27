package mnshat.dev.myproject.users.patient.calender.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentCreateOwnActivityBinding


@AndroidEntryPoint
class CreateOwnActivityFragment : BaseFragment() {

    private lateinit var binding: FragmentCreateOwnActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateOwnActivityBinding.inflate(inflater, container, false)
setListener()
        return binding.root

    }

    private fun setListener() {
        binding.change.setOnClickListener{
            ChooseDayFragment().show(childFragmentManager, ChooseDayFragment::class.java.name)
        }
    }


}