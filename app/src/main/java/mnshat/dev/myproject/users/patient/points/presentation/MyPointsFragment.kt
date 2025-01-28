package mnshat.dev.myproject.users.patient.points.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentMyPointsBinding
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.CurrentDay
import mnshat.dev.myproject.util.CURRENT_DAY
import mnshat.dev.myproject.util.SharedPreferencesManager


class MyPointsFragment : BaseFragment() {

    private lateinit var binding: FragmentMyPointsBinding
    private lateinit var  adapterPoints: AdapterPoints

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPointsBinding.inflate(inflater, container, false)
        setUpRecycleriew(getCurrentDayLocally())
        return  binding.root
    }

    private fun setUpRecycleriew(i: Int) {
        adapterPoints = AdapterPoints(i)
        val gridLayoutManager = GridLayoutManager(requireContext(),3)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = adapterPoints

    }
    private fun getCurrentDayLocally(): Int {
        val sharedPreferences = SharedPreferencesManager(requireActivity())
        val string = sharedPreferences.getString(CURRENT_DAY, null.toString())
        val gson = Gson()
        val currentDay = gson.fromJson(string, CurrentDay::class.java)
        return currentDay.status?.day?.minus(1)!!

    }


}