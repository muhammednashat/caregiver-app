package mnshat.dev.myproject.commonFeatures.chatting

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.factories.ChatViewModelFactory

abstract class BaseChattingFragment<T: ViewDataBinding> : BaseFragment<T>()
     {

     lateinit var viewModel: ChatViewModel


    override fun initializeViews() {
        super.initializeViews()
        initViewModel()
    }


         private fun initViewModel() {
               val factory = ChatViewModelFactory(sharedPreferences, activity?.application!!)
                viewModel = ViewModelProvider(requireActivity(), factory)[ChatViewModel::class.java]
               observeViewModel()
         }

         open fun observeViewModel() {

         }


}