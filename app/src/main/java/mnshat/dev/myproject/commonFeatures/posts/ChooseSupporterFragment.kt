package mnshat.dev.myproject.commonFeatures.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.ChooseSupporterAdapter
import mnshat.dev.myproject.auth.data.entity.UserProfile
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentChooseSupporterBinding
import mnshat.dev.myproject.interfaces.OnSendButtonClicked
import mnshat.dev.myproject.users.patient.tools.supplications.prisentation.SupplicationViewModel
import kotlin.getValue

@AndroidEntryPoint

class ChooseSupporterFragment : BaseBottomSheetDialogFragment() {

    private val viewModel: SupplicationViewModel by activityViewModels()
    private lateinit var binding: FragmentChooseSupporterBinding

    private lateinit var onSendButtonClicked: OnSendButtonClicked
    private lateinit var chooseAdapter: ChooseSupporterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseSupporterBinding.inflate(inflater,container,false)
        viewModel.retrieveSupporters()
        setupClickListener()
        observeViewModel()
        return  binding.root
    }


   private  fun setupClickListener() {
       binding.close.setOnClickListener {
           dismiss()
       }

        binding.send.setOnClickListener {

            if (chooseAdapter.getSelectedSupporters().size == 0) {
                showToast(getString(R.string.please_select_supporter))
            } else {
                showProgressDialog()
                viewModel.shareSupplication(chooseAdapter.getSelectedSupporters())
            }
        }
    }

    fun initOnConfirmButtonClicked(onSendButtonClicked: OnSendButtonClicked) {
        this.onSendButtonClicked = onSendButtonClicked
    }

    private fun observeViewModel(){

        viewModel.statusSharing.observe(this){
            if (it){
                dismissProgressDialog()
                showToast(getString(R.string.shared_successfully))
                dismiss()
            }
        }

        viewModel.supportersProfile.observe (this){
            it?.let {
                updateUi(it)
            }
        }
    }

    private fun updateUi(it: List<UserProfile>) {
        chooseAdapter = ChooseSupporterAdapter(requireActivity(), it)
        binding.supportersRecyclerView.apply {
            adapter = chooseAdapter
            setHasFixedSize(true)
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                requireContext(),
                androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.loaderProgress.visibility = View.GONE
            binding.send.visibility = View.VISIBLE
            alpha = 1.0f
        }

    }


}