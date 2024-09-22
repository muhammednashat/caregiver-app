package mnshat.dev.myproject.users.caregiver

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.RecyclerAdapter
import mnshat.dev.myproject.databinding.FragmentPatientBinding
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.data.itemList


class PatientFragment : BaseCaregiverFragment<FragmentPatientBinding>() {



    override fun getLayout()= R.layout.fragment_patient

    override fun setupClickListener() {
        super.setupClickListener()

//        binding.rootEducationalContent.setOnClickListener {
//            startActivity(Intent(requireActivity(), LibraryActivity::class.java))
//        }

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