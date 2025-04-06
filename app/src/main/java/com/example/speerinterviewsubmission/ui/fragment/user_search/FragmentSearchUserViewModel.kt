package com.example.speerinterviewsubmission.ui.fragment.user_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.speerinterviewsubmission.data.repos.UserRepo

class FragmentSearchUserViewModel(private val repo:UserRepo) : ViewModel() {
        fun searchUser(name:String) = repo.searchUser(name)
        fun getFollowers(name:String) = repo.getFollowers(name)
        fun getFollowings(name:String) = repo.getFollowings(name)
}