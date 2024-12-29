package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.BaseFragment
import mnshat.dev.myproject.R


@AndroidEntryPoint
class DayTaskFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_day_task, container, false)
    }

}