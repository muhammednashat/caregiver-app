package mnshat.dev.myproject.commonFeatures.sharingcontent

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.SharingAdapter
import mnshat.dev.myproject.databinding.FragmentSharingListBinding
import mnshat.dev.myproject.factories.SharingViewModelFactory
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.log


class SharingListFragment : BasePatientFragment<FragmentSharingListBinding>() {

    lateinit var viewModel: SharingViewModel


    override fun getLayout() = R.layout.fragment_sharing_list


    override fun setupClickListener() {

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initializeViews() {
        initViewModel()

        retrieveSharedList()

        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }

    }

    private fun retrieveSharedList() {
        binding.noItems.visibility = android.view.View.GONE
        showProgressDialog()
        viewModel.retrieveSharedList()
    }

    private fun initViewModel() {
        val factory = SharingViewModelFactory(sharedPreferences, activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory)[SharingViewModel::class.java]

        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.posts.observe(viewLifecycleOwner) { list ->
         if (list?.size!! > 0){
             log("list size ${list.size}")
             binding.recyclerView.adapter = SharingAdapter(list)
             binding.noItems.visibility = android.view.View.GONE
             binding.contentList.visibility = android.view.View.VISIBLE

         }else{
             binding.noItems.visibility = android.view.View.VISIBLE
             binding.contentList.visibility = android.view.View.GONE
             log("list size 0")
         }
            dismissProgressDialog()
        }

    }

}