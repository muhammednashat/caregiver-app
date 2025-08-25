package mnshat.dev.myproject.chatting.presintation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentMessagesListBinding
import mnshat.dev.myproject.interfaces.ItemMessagesListClicked
import mnshat.dev.myproject.chatting.entity.Chatting
import kotlin.getValue


class MessagesListFragment : BaseFragment(),ItemMessagesListClicked {

    private val viewModel: ChatViewModel by activityViewModels()
    private lateinit var binding: FragmentMessagesListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessagesListBinding.inflate(inflater, container, false)
        checkInternetConnection()
        return binding.root
    }
    private fun checkInternetConnection() {
        if (isConnected()) {
            initializeViews()
        } else {
            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.no_internet_connection))
            .setMessage(getString(R.string.please_check_your_internet_connection_and_try_again))
            .setPositiveButton(getString(R.string.try_again)) { dialog, _ ->
                checkInternetConnection()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun initializeViews() {
        showProgressDialog()
        setupClickListener()
        observeViewModel()
    }



    private fun setupClickListener() {

        binding.btStartMessaging.setOnClickListener {
            checkInternet()
        }
        binding.icAdd.setOnClickListener {
            checkInternet()
        }

    }
    private fun checkInternet() {
        if (isConnected()) {
            chooseUserToChatWith()
        } else {
            showNoInternetSnackBar(binding.root)
        }
    }



    private fun chooseUserToChatWith() {

        if (viewModel.isUserCaregiver()) {
            ChooseUserToChatFragment().show(childFragmentManager, ChooseUserToChatFragment::class.java.name)
        } else {
            if (viewModel.hasPartner()){
                ChooseUserToChatFragment().show(childFragmentManager, ChooseUserToChatFragment::class.java.name)
            }else{
                showToast(getString(R.string.no_supporters_text))
            }
        }


    }

    private fun observeViewModel() {

        viewModel.chattingList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.messages.visibility = View.GONE
                binding.noMessage.visibility = View.VISIBLE
            }else{
                updateUIWithMessagesList(it)
                binding.noMessage.visibility = View.GONE
                binding.messages.visibility = View.VISIBLE
            }
            dismissProgressDialog()
        }
    }



    private fun updateUIWithMessagesList(chattingList: List<Chatting>) {
        binding.messagesRecyclerView.apply {
            adapter = MessagesListAdapter(chattingList,requireActivity(),viewModel.sharedPreferences,this@MessagesListFragment)
        }
    }

    override fun onItemClicked(name: String, idPartner: String, urlImage: String) {
        viewModel.partnerId = idPartner
        val action = MessagesListFragmentDirections.actionMessagesListFragmentToChatFragment(idPartner,urlImage,name)
        findNavController().navigate(action)

    }




}