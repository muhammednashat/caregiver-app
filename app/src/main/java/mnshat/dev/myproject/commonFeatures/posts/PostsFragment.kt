package mnshat.dev.myproject.commonFeatures.posts

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.PostsAdapter
import mnshat.dev.myproject.databinding.FragmentPostsBinding
import mnshat.dev.myproject.factories.SharingViewModelFactory
import mnshat.dev.myproject.interfaces.ItemPostsClicked
import mnshat.dev.myproject.model.Gratitude
import mnshat.dev.myproject.model.LibraryContent
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.users.patient.main.BasePatientFragment
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.GRATITUDE
import mnshat.dev.myproject.util.LIBRARY
import mnshat.dev.myproject.util.SUPPLICATIONS
import mnshat.dev.myproject.util.log


class PostsFragment : BasePatientFragment<FragmentPostsBinding>(), ItemPostsClicked {

    lateinit var viewModel: PostsViewModel


    override fun getLayout() = R.layout.fragment_posts


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
        viewModel = ViewModelProvider(requireActivity(), factory)[PostsViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.posts.observe(viewLifecycleOwner) { list ->
         if (list?.size!! > 0){
             log("list size ${list.size}")
             binding.recyclerView.adapter = PostsAdapter(list,requireActivity(),sharedPreferences,this)
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

    override fun onItemClicked(post: Post) {
        when(post.type){
            GRATITUDE ->  navigateToGratitude(post.gratitude)
            SUPPLICATIONS -> navigateToSupplication(post.supplication)
            LIBRARY -> navigateToLibrary(post.libraryContent)
        }
    }

    private fun navigateToSupplication(supplication: Supplication?) {

    }

    private fun navigateToLibrary(libraryContent: LibraryContent?) {

    }

    private fun navigateToGratitude(gratitude: Gratitude?) {

    }

}