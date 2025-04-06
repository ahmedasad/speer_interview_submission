package com.example.speerinterviewsubmission.data.repos

import androidx.lifecycle.LiveData
import com.example.speerinterviewsubmission.data.model.User
import com.example.speerinterviewsubmission.data.network.APIResponse
import com.example.speerinterviewsubmission.data.network.Client

class UserRepo : BaseRepo() {

    fun searchUser(name: String): LiveData<APIResponse<User>> =
        getNetworkResult { Client.getUserServices().getSearchUser(name) }

    fun getFollowers(name: String): LiveData<APIResponse<ArrayList<User>>> =
        getNetworkResult { Client.getUserServices().getFollowers(name) }

    fun getFollowings(name: String): LiveData<APIResponse<ArrayList<User>>> =
        getNetworkResult { Client.getUserServices().getFollowing(name) }

}