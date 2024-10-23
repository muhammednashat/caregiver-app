package mnshat.dev.myproject.commonFeatures.sharingcontent

import android.view.View
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.ChooseSupporterAdapter
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentChooseSupporterBinding
import mnshat.dev.myproject.firebase.FirebaseService
import mnshat.dev.myproject.model.RegistrationData
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.HAS_PARTNER
import mnshat.dev.myproject.util.log


class ChooseSupporterFragment : BaseBottomSheetDialogFragment<FragmentChooseSupporterBinding>() {



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
            adapter?.let {
                log(it.getSelectedSupporters().size.toString())
                it.getSelectedSupporters().forEach {
                    log(it)
                }
            }
        }

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

    private fun updateUi(it: List<RegistrationData>) {
        binding.supportersRecyclerView.apply {
            adapter = ChooseSupporterAdapter(requireContext(), it)
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