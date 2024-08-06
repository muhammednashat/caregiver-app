package mnshat.dev.myproject.users.patient.main.tools.breath

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentMainBreathBinding
import mnshat.dev.myproject.databinding.FragmentMainSupplicationsBinding
import mnshat.dev.myproject.users.patient.main.BasePatientFragment


class MainBreathFragment : BasePatientFragment<FragmentMainBreathBinding>(){
    override fun initializeViews() {
        super.initializeViews()
        binding.remainingTimeFormat.text = getString(R.string.remaining_time_format,3,"ثانيه متبقية")
    }

    override fun getLayout()= R.layout.fragment_main_breath


}