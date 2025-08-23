package mnshat.dev.myproject.chatting.presintation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.adapters.SupportersChattingAdapter
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentChooseUserToChatBinding
import mnshat.dev.myproject.interfaces.ItemMessagesListClicked

class ChooseUserToChatFragment : BaseBottomSheetDialogFragment() ,
    ItemMessagesListClicked {

    private val viewModel: ChatViewModel by activityViewModels()
    private lateinit var binding: FragmentChooseUserToChatBinding
    private lateinit var adapter: SupportersChattingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseUserToChatBinding.inflate(inflater, container, false)
        viewModel.retrievePartners()
        observeViewModel()
        setupClickListener()
        return binding.root


    }
    private fun setupClickListener() {

        binding.close.setOnClickListener {
            dismiss()
        }

    }

    private  fun observeViewModel(){
      viewModel.partners.observe(viewLifecycleOwner){
          if (it!=null){
              adapter = SupportersChattingAdapter(it,requireActivity(),this)
              binding.recyclerView.adapter = adapter
          }
      }
    }
    override fun onItemClicked(name: String, idPartner: String, urlImage: String) {
        navigateToChatFragment( idPartner, urlImage, name)
        dismiss()
    }
    private fun navigateToChatFragment(id: String, urlImage: String, name: String) {
        val action =
            MessagesListFragmentDirections
                .actionMessagesListFragmentToChatFragment(
                    id,
                    urlImage,
                    name,
                )
        findNavController().navigate(action)
    }



}