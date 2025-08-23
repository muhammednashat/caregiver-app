package mnshat.dev.myproject.posts.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mnshat.dev.myproject.posts.data.PostsRepo
import mnshat.dev.myproject.model.Post
import mnshat.dev.myproject.users.patient.supporters.data.repos.SupportersRepo
import mnshat.dev.myproject.util.CAREGIVER
import mnshat.dev.myproject.util.SharedPreferencesManager
import mnshat.dev.myproject.util.log
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val supportersRepo: SupportersRepo,
    private val postsRepo: PostsRepo,
    val sharedPreferences: SharedPreferencesManager,
) : ViewModel() {


    private val _posts = MutableLiveData<List<Post>?>()
    val posts: LiveData<List<Post>?>
        get() = _posts


    val user = postsRepo.getUser()
    val supportersProfile = supportersRepo.supportersProfile

    private val _statusSharing = MutableLiveData<Boolean>()
    val statusSharing: LiveData<Boolean> get() = _statusSharing


    fun sharePost(post: Post) {
        try {
            viewModelScope.launch {
                postsRepo.shareContent(post)
                _statusSharing.value = true
            }
        } catch (e: Exception) {
            _statusSharing.value = false

        }
    }

    fun retrieveSupporters() {
        viewModelScope.launch {
            supportersRepo.retrievePartnersIds(supportersRepo.userProfile().id!!)
        }
    }

    fun retrievePostsRemotely() {
        viewModelScope.launch {
            val data = postsRepo.retrieveSharedList()
            if (data != null) {
                log("data is not null")
                filterData(data.posts)
            }else{
                log("data is null")
                _posts.value = null
            }
        }
    }

    private fun filterData(posts: MutableList<Post>?) {
        log("user type is ${user.typeOfUser}")
     if (user.typeOfUser == CAREGIVER){
         log("user type is caregiver")
         val list = posts?.filter {isContainUser(it.supporters!!,user.partnerEmail!!)}
         _posts.value = list
     }else{
         log("user type is patient")
         _posts.value = posts
     }
    }

    private fun isContainUser(list: List<String>, email: String): Boolean {
        list.forEach {
            if (it == email) {
                return true
            }
        }
        return false
    }

}