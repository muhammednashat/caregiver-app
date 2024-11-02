package mnshat.dev.myproject.commonFeatures.posts

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
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
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.AUDIO
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.EMAIL_PARTNER
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.GRATITUDE
import mnshat.dev.myproject.util.LIBRARY
import mnshat.dev.myproject.util.SUPPLICATIONS
import mnshat.dev.myproject.util.TYPE_OF_USER
import mnshat.dev.myproject.util.USER_EMAIL
import mnshat.dev.myproject.util.VIDEO
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
        if (sharedPreferences.getString(TYPE_OF_USER) == CAREGIVER){
            log("caregiver ${sharedPreferences.getString(EMAIL_PARTNER)}")
            retrieveSharedList(sharedPreferences.getString(EMAIL_PARTNER))
        }else{
            log("Patient ${sharedPreferences.getString(USER_EMAIL)}")

            retrieveSharedList(sharedPreferences.getString(USER_EMAIL))

        }

        if (currentLang != ENGLISH_KEY) {
            binding.icBack.setBackgroundDrawable(resources.getDrawable(R.drawable.background_back_right))
        }

    }

    private fun retrieveSharedList(email:String ) {
        binding.noItems.visibility = android.view.View.GONE
        showProgressDialog()
        viewModel.retrieveSharedList(email)
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
             updateUi(list)
         }else{
             binding.noItems.visibility = View.VISIBLE
             binding.contentList.visibility = android.view.View.GONE
             log("list size 0")
         }
            dismissProgressDialog()
        }

    }

    private fun updateUi(data: List<Post>) {
        var list = data
        if (sharedPreferences.getString(TYPE_OF_USER) == CAREGIVER){
        list = list.filter {isContainUser(it.supporters!!,sharedPreferences.getString(USER_EMAIL))}
        log("caregiver")
        }

        binding.recyclerView.adapter =
            PostsAdapter(list, requireActivity(), sharedPreferences, this)
        binding.noItems.visibility = View.GONE
        binding.contentList.visibility = View.VISIBLE
    }

private fun isContainUser(list: List<String> , email:String) =  list.contains(email)


    override fun onItemClicked(post: Post) {
        when(post.type){
            GRATITUDE ->  navigateToGratitude(post.gratitude!!)
            SUPPLICATIONS -> navigateToSupplication(post.supplication)
            LIBRARY -> navigateToLibrary(post.libraryContent)
        }
    }

    private fun navigateToSupplication(supplication: Supplication?) {
        val action = PostsFragmentDirections.actionPostsFragment2ToDisplaySupplicationFragment(supplication!!)
        findNavController().navigate(action)
    }

    private fun navigateToLibrary(libraryContent: LibraryContent?) {
       lateinit var action:NavDirections
        when(libraryContent!!.type){
            ARTICLE ->  action = PostsFragmentDirections.actionPostsFragment2ToDisplayArticleFragment(libraryContent)
            VIDEO -> action = PostsFragmentDirections.actionPostsFragment2ToPlayVideoFragment(libraryContent)
            AUDIO -> action = PostsFragmentDirections.actionPostsFragment2ToPlayAudioFragment(libraryContent)
        }
        findNavController().navigate(action)

    }

    private fun navigateToGratitude(gratitude: Gratitude) {
     val action = PostsFragmentDirections.actionPostsFragment2ToDisplayGratitudeFragment(gratitude)
    findNavController().navigate(action)
    }

}