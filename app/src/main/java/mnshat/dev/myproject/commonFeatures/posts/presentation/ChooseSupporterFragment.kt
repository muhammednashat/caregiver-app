package mnshat.dev.myproject.commonFeatures.posts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.ChooseSupporterAdapter
import mnshat.dev.myproject.auth.data.entity.UserProfile
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.commonFeatures.posts.OnSendButtonClicked
import mnshat.dev.myproject.databinding.FragmentChooseSupporterBinding
import kotlin.getValue

@AndroidEntryPoint

class ChooseSupporterFragment : BaseBottomSheetDialogFragment() {

    private val viewModel: PostsViewModel by activityViewModels()
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
                onSendButtonClicked.onSendClicked(chooseAdapter.getSelectedSupporters())
                dismiss()
            }

        }
    }

    fun initOnConfirmButtonClicked(onSendButtonClicked: OnSendButtonClicked) {
        this.onSendButtonClicked = onSendButtonClicked
    }

    private fun observeViewModel(){
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
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.loaderProgress.visibility = View.GONE
            binding.send.visibility = View.VISIBLE
            alpha = 1.0f
        }

    }


}