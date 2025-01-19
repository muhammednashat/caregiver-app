package mnshat.dev.myproject.users.patient.points.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentMyPointsBinding


class MyPointsFragment : BaseFragment() {

    private lateinit var binding: FragmentMyPointsBinding
    private lateinit var  adapterPoints: AdapterPoints

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPointsBinding.inflate(inflater, container, false)
        setUpRecycleriew(2)
        return  binding.root
    }

    private fun setUpRecycleriew(i: Int) {
        adapterPoints = AdapterPoints(i)
        val gridLayoutManager = GridLayoutManager(requireContext(),3)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = adapterPoints

    }

}