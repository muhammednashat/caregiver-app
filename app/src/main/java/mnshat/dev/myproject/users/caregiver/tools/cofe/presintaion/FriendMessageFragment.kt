package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentFriendMessageBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.firebase.FirebaseService.userProfiles
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.users.patient.tools.coffeeideas.presentaion.CofeViewModel
import mnshat.dev.myproject.util.EMAIL_PARTNER
import mnshat.dev.myproject.util.PARTNER_PROFILE
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log
import androidx.core.graphics.toColorInt

@AndroidEntryPoint
class FriendMessageFragment : BaseFragment() {

    private lateinit var binding: FragmentFriendMessageBinding
   private val viewModel: CofeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFriendMessageBinding.inflate(inflater, container, false)
        binding.constraintNext.setOnClickListener {
            findNavController().navigate(R.id.action_friendMessageFragment_to_cupsMeaningFragment)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        getPartnerData()
        listenToData()
        return binding.root
    }

    private fun getPartnerData(){
        FirebaseService.retrieveUser(null,
            viewModel.sharedPreferences.getString(EMAIL_PARTNER)){
            if (it == null){
            }else{
           loadImage(requireActivity(),it.imageUser,binding.imageView)
                binding.nameUser.text = it.name
           viewModel.sharedPreferences.storeObject(PARTNER_PROFILE,it)
            }
        }
    }

    private fun listenToData() {
        userProfiles.child(viewModel.sharedPreferences.getString(USER_ID)).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(RegistrationData::class.java)
                    if(user?.userIdea?.idea?.isNotEmpty() == true ){
                        binding.message.text = user.userIdea?.idea
                        binding.constraintNext.visibility = View.VISIBLE
                        viewModel.sharedPreferences.storeString("userIdea", user.userIdea?.idea)
                        viewModel.sharedPreferences.storeInt("cupIdea", user.userIdea?.cupIdea)
                        setUpCupInfo(user.userIdea?.cupIdea)
                        binding.cupInfo.visibility = View.VISIBLE

                    }else{
                        binding.message.text =
                            getString(R.string.no_ideas_received_yet_nyou_can_only_continue_after_receiving_a_message_from_your_friend)
                    }
                    log("idea is ${user?.userIdea?.idea}")
                    log("response is ${user?.userIdea?.response}")
                } else {
                    log("supporter is null")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                log("Database error: ${error.message}")
            }
        })
    }

    private fun setUpCupInfo(cupIdea: Int?) {
        when (cupIdea) {
           2-> {
                updateUiData(getString(R.string.over_sugary_coffee),getString(R.string.unrealistic_thinking),getString(R.string.ignoring_challenges),"#f4aa34",R.drawable.image_cup2)
            }
          3 -> {
               updateUiData(getString(R.string.balanced_coffee),getString(R.string.realistic_thinking),getString(R.string.acknowledge_the_difficulties),"#5cb348",R.drawable.image_cup3)
            }


    }


}

    private fun updateUiData(text1: String, text2: String, text3: String, color:String, image: Int) {
        binding.typeCofe.text = text1
        binding.typeCofe.setTextColor(color.toColorInt())
        binding.feeling.text = text2
        binding.example.text = text3
        binding.coffe.setImageResource(image)
    }
}