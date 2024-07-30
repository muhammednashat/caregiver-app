package mnshat.dev.myproject.users.patient.main.tools.supplications

import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.FragmentSupplicationsBinding
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.main.BasePatientFragment


class SupplicationsFragment : BasePatientFragment<FragmentSupplicationsBinding>() {

    private lateinit var supplication: Supplication

    override fun initializeViews() {
        arguments?.let {
            supplication = it.getParcelable("supplication")!!
        }
    }


    override fun getLayout()= R.layout.fragment_supplications

}