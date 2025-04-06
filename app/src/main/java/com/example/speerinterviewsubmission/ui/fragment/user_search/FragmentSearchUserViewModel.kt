package com.example.speerinterviewsubmission.ui.fragment.user_search


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.speerinterviewsubmission.data.model.User
import com.example.speerinterviewsubmission.data.repos.UserRepo

class FragmentSearchUserViewModel(private val repo:UserRepo) : ViewModel() {

        var userLogin:String? = null
        fun searchUser(name:String) = repo.searchUser(name)
}