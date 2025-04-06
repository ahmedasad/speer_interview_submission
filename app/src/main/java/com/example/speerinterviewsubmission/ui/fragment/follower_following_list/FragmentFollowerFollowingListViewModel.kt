package com.example.speerinterviewsubmission.ui.fragment.follower_following_list

import androidx.lifecycle.ViewModel
import com.example.speerinterviewsubmission.data.repos.UserRepo

class FragmentFollowerFollowingListViewModel(private val repo:UserRepo) : ViewModel() {


    var userLogin:String? = null
    var count:String? = null
    var listTag:String? = null

    fun getFollowers(name:String) = repo.getFollowers(name)
    fun getFollowings(name:String) = repo.getFollowings(name)
}