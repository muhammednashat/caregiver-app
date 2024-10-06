package mnshat.dev.myproject.features.chatting

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseBottomSheetDialogFragment
import mnshat.dev.myproject.databinding.FragmentChooseUserToChatBinding
import mnshat.dev.myproject.factories.ChatViewModelFactory
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.ID_PARTNER
import mnshat.dev.myproject.util.IMAGE_PARTNER
import mnshat.dev.myproject.util.NAME_PARTNER
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.loadImage
import mnshat.dev.myproject.util.log


class ChooseUserToChatFragment : BaseBottomSheetDialogFragment<FragmentChooseUserToChatBinding>() {

    lateinit var viewModel: ChatViewModel

    override fun getLayout() = R.layout.fragment_choose_user_to_chat

    override fun setupClickListener() {

        binding.close.setOnClickListener {
            dismiss()
        }
        binding.itemUser.setOnClickListener {
            navigateToChatFragment()
        }
    }

    private fun navigateToChatFragment() {
        val action =
            MessagesListFragmentDirections
                .actionMessagesListFragmentToChatFragment(
                    sharedPreferences.getString(ID_PARTNER),
                    sharedPreferences.getString(IMAGE_PARTNER),
                    sharedPreferences.getString(NAME_PARTNER),
                )
        findNavController().navigate(action)
    }

    override fun initializeViews() {

        if (currentLang != ENGLISH_KEY) {
            binding.close.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
            binding.root.setBackgroundDrawable(resources.getDrawable(R.drawable.corner_top_lift))
        }
        checkUser()
        initViewModel()

    }

    private fun checkUser() {

        if (sharedPreferences.getString(TYPE_OF_USER) == CAREGIVER) {

            binding.itemUser.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE

            initUserData()

        } else {
            binding.itemUser.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE

        }

    }

    private fun initUserData() {
     binding.name.text = sharedPreferences.getString(NAME_PARTNER)
        loadImage(requireActivity(),sharedPreferences.getString(IMAGE_PARTNER),binding.imageUser)

    }


    private fun initViewModel() {
        val factory = ChatViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[ChatViewModel::class.java]
    }

}