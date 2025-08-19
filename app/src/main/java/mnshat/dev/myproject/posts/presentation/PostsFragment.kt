package mnshat.dev.myproject.posts.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.PostsAdapter
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentPostsBinding
import mnshat.dev.myproject.interfaces.ItemPostsClicked
import mnshat.dev.myproject.users.patient.tools.gratitude.entity.Gratitude
import mnshat.dev.myproject.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.ARTICLE
import mnshat.dev.myproject.util.AUDIO
import mnshat.dev.myproject.util.GRATITUDE
import mnshat.dev.myproject.util.LIBRARY
import mnshat.dev.myproject.util.SUPPLICATIONS
import mnshat.dev.myproject.util.VIDEO

@AndroidEntryPoint
class PostsFragment : BaseFragment(), ItemPostsClicked {

  private val viewModel: PostsViewModel by viewModels()
 private lateinit var binding: FragmentPostsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPostsBinding.inflate(inflater, container, false)
        checkInternetConnection()
        return binding.root


    }

    private fun checkInternetConnection() {
        if (isConnected()) {
            binding.noItems.visibility = View.GONE
            viewModel.retrievePostsRemotely()
            observeViewModel()
            setupClickListener()
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

    private fun setupClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun observeViewModel() {

        viewModel.posts.observe(viewLifecycleOwner) { list ->
         if (list?.size!! > 0){
             updateUi(list)
         }else{
             binding.noItems.visibility = View.VISIBLE
             binding.contentList.visibility = View.GONE
         }
            dismissProgressDialog()
        }

    }

    private fun updateUi(data: List<Post>) {
            binding.recyclerView.adapter =
            PostsAdapter(data, requireActivity(), viewModel.sharedPreferences, this)
            binding.noItems.visibility = View.GONE
            binding.contentList.visibility = View.VISIBLE

    }




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