package mnshat.dev.myproject.users.caregiver.partner

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.ItemClickListener
import mnshat.dev.myproject.adapters.RecyclerAdapter
import mnshat.dev.myproject.commonFeatures.chatting.MessagesListFragmentDirections
import mnshat.dev.myproject.databinding.FragmentPatientBinding
import mnshat.dev.myproject.users.caregiver.main.BaseCaregiverFragment
import mnshat.dev.myproject.util.ID_PARTNER
import mnshat.dev.myproject.util.IMAGE_PARTNER
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.MOOD
import mnshat.dev.myproject.util.NAME_PARTNER
import mnshat.dev.myproject.util.PERMISSION_MASSAGE
import mnshat.dev.myproject.util.PERMISSION_MOOD
import mnshat.dev.myproject.util.PERMISSION_POINTS
import mnshat.dev.myproject.util.PROGRAM
import mnshat.dev.myproject.util.SHARING
import mnshat.dev.myproject.util.data.itemList
import mnshat.dev.myproject.util.loadImage


class PatientFragment : BaseCaregiverFragment<FragmentPatientBinding>(), ItemClickListener {

    override fun getLayout()= R.layout.fragment_patient

    override fun setupClickListener() {
        super.setupClickListener()

        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.messaging.setOnClickListener {

            if (sharedPreferences.getBoolean(PERMISSION_MASSAGE) ) {
                navigateToChatWithPatient(sharedPreferences.getString(NAME_PARTNER),sharedPreferences.getString(
                    ID_PARTNER),sharedPreferences.getString(IMAGE_PARTNER))
            }else{
                showToast(getString(R.string.you_do_not_have_access_to_send_a_private_message))
            }
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
            RecyclerAdapter(itemList(), sharedPreferences.getString(LANGUAGE),this)
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.recycler.layoutManager =
            gridLayoutManager
        binding.recycler.adapter = adapter
    }

    override fun onItemClick(flag: String) {
        when(flag){
            PROGRAM -> {
                if (sharedPreferences.getBoolean(PERMISSION_POINTS) ) {
                    findNavController().navigate(R.id.action_usersFragment_to_myPointsFragment2)
                }else{
                    showToast(getString(R.string.you_do_not_have_permission_to_view_the_daily_program))
                }
            }
            SHARING -> {
                    findNavController().navigate(R.id.action_usersFragment_to_postsFragment2)
            }
            MOOD -> {
                if (sharedPreferences.getBoolean(PERMISSION_MOOD) ) {
                    findNavController().navigate(R.id.action_usersFragment_to_trackingMoodFragment2)
                }else{
                    showToast(getString(R.string.you_do_not_have_access_to_the_mood))
                }
            }
        }
        }
    }


