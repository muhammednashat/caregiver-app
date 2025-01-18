package mnshat.dev.myproject.users.patient.points.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentMyPointsBinding


class MyPointsFragment : BaseFragment() {

    private lateinit var binding: FragmentMyPointsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPointsBinding.inflate(inflater, container, false)
        return  binding.root


    }

}