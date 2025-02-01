package mnshat.dev.myproject.commonFeatures.posts

import android.view.View
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.ChooseSupporterAdapter
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment2
import mnshat.dev.myproject.databinding.FragmentChooseSupporterBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.interfaces.OnSendButtonClicked
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.USER_ID
import mnshat.dev.myproject.util.log

// ToDo check it  , Fragment ChooseSupporterFragment{ea89e49} (8b694ba6-c9a2-43aa-85ca-30a6d9987f3f) not attached to an activity.
class ChooseSupporterFragment : BaseBottomSheetDialogFragment2<FragmentChooseSupporterBinding>() {

    private lateinit var onSendButtonClicked: OnSendButtonClicked

override fun getLayout() = R.layout.fragment_choose_supporter
private lateinit var chooseAdapter: ChooseSupporterAdapter
    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }

        retrieveUsers()
    }

    override fun setupClickListener() {
        super.setupClickListener()
        binding.send.setOnClickListener {

            if (chooseAdapter.getSelectedSupporters().size == 0){
                showToast("Please Select Supporter")
            }else{
                onSendButtonClicked.onSendClicked(chooseAdapter.getSelectedSupporters())
                dismiss()
            }
        }
    }
      fun initOnConfirmButtonClicked(onSendButtonClicked: OnSendButtonClicked){
        this.onSendButtonClicked = onSendButtonClicked
    }

    private fun retrieveUsers() {
        FirebaseService.retrieveUser(sharedPreferences.getString(USER_ID)){ user ->
            user?.storeDataLocally(sharedPreferences)
            FirebaseService.retrieveUsers(user?.supports){
                it?.let {
                    updateUi(it)
                }
            }
        }
    }

    private fun updateUi(it: List<RegistrationData>) {
        chooseAdapter = ChooseSupporterAdapter(requireActivity(), it)
        binding.supportersRecyclerView.apply {
            adapter = chooseAdapter
            setHasFixedSize(true)
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                requireContext(),
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false)
            binding.loaderProgress.visibility = View.GONE
            binding.send.visibility = View.VISIBLE
            alpha = 1.0f
        }

    }


}