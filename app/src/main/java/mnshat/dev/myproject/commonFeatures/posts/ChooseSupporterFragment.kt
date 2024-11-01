package mnshat.dev.myproject.commonFeatures.posts

import android.view.View
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.ChooseSupporterAdapter
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentChooseSupporterBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.interfaces.OnSendButtonClicked
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.log


class ChooseSupporterFragment : BaseBottomSheetDialogFragment<FragmentChooseSupporterBinding>() {

    private lateinit var onSendButtonClicked: OnSendButtonClicked

override fun getLayout() = R.layout.fragment_choose_supporter
private lateinit var adapter: ChooseSupporterAdapter
    override fun initializeViews() {
        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
       retrieveSupporters()
    }

    override fun setupClickListener() {
        super.setupClickListener()
        binding.send.setOnClickListener {

            if (adapter.getSelectedSupporters().size == 0){
                showToast("Please Select Supporter")
            }else{
                onSendButtonClicked.onSendClicked(adapter.getSelectedSupporters())
                dismiss()
            }
        }
    }
      fun initOnConfirmButtonClicked(onSendButtonClicked: OnSendButtonClicked){
        this.onSendButtonClicked = onSendButtonClicked
    }

    private fun retrieveSupporters() {
        FirebaseService.listenForUserDataChanges {
            it?.let {
                it.storeDataLocally(sharedPreferences)
                if (sharedPreferences.getBoolean(HAS_PARTNER)){
                    FirebaseService.retrieveUsersByEmails(it.supports){
                        it?.let {
                            log("${it.toString()} ")
                            updateUi(it)
                        }
                    }
                }
                else{
                    log("No Supporter ")
                }
                dismissProgressDialog()
            }
        }
    }

    //ToDo (205673de-74d2-4caa-bdea-bd2340dc73fe) not attached to a context.
    private fun updateUi(it: List<RegistrationData>) {
        adapter = ChooseSupporterAdapter(requireContext(), it)
        binding.supportersRecyclerView.apply {
            adapter = this@ChooseSupporterFragment.adapter
            setHasFixedSize(true)
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                requireContext(),
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false)
            binding.loaderProgress.visibility = View.GONE
            alpha = 1.0f
        }

    }


}