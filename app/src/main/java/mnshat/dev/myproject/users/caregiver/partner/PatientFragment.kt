package mnshat.dev.myproject.users.caregiver.partner

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.RecyclerAdapter
import mnshat.dev.myproject.databinding.FragmentPatientBinding
import mnshat.dev.myproject.users.caregiver.BaseCaregiverFragment
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.data.itemList


class PatientFragment : BaseCaregiverFragment<FragmentPatientBinding>() {



    override fun getLayout()= R.layout.fragment_patient

    override fun setupClickListener() {
        super.setupClickListener()

        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun initializeViews() {
        super.initializeViews()

        val adapter = RecyclerAdapter(itemList(),sharedPreferences.getString(LANGUAGE),requireContext())
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.recycler.layoutManager =
            gridLayoutManager
        binding.recycler.adapter = adapter

    }




}