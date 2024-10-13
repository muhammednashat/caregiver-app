package mnshat.dev.myproject.users.caregiver.partner

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.RecyclerAdapter
import mnshat.dev.myproject.commonFeatures.chatting.MessagesListFragmentDirections
import mnshat.dev.myproject.databinding.FragmentPatientBinding
import mnshat.dev.myproject.users.caregiver.main.BaseCaregiverFragment
import mnshat.dev.myproject.util.ID_PARTNER
import mnshat.dev.myproject.util.IMAGE_PARTNER
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.NAME_PARTNER
import mnshat.dev.myproject.util.data.itemList
import mnshat.dev.myproject.util.loadImage


class PatientFragment : BaseCaregiverFragment<FragmentPatientBinding>() {

    override fun getLayout()= R.layout.fragment_patient

    override fun setupClickListener() {
        super.setupClickListener()

        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.messaging.setOnClickListener {
          navigateToChatWithPatient(sharedPreferences.getString(NAME_PARTNER),sharedPreferences.getString(
              ID_PARTNER),sharedPreferences.getString(IMAGE_PARTNER))
        }

    }

    private fun navigateToChatWithPatient(name: String, idPartner: String, urlImage: String) {
        val action = PatientFragmentDirections.actionUsersFragmentToChatFragment(idPartner,urlImage,name)
        findNavController().navigate(action)
    }

    override fun initializeViews() {
        super.initializeViews()

        initDataUser()
        setUpRecyclerData()

    }

    private fun initDataUser() {

        loadImage(requireActivity(),sharedPreferences.getString(IMAGE_PARTNER), binding.imageView)
        binding.name.text = sharedPreferences.getString(NAME_PARTNER)
    }

    private fun setUpRecyclerData() {
        val adapter =
            RecyclerAdapter(itemList(), sharedPreferences.getString(LANGUAGE), requireContext())
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.recycler.layoutManager =
            gridLayoutManager
        binding.recycler.adapter = adapter
    }



}