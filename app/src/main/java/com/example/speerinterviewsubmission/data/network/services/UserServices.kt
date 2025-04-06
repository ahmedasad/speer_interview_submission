package com.example.speerinterviewsubmission.data.network.services

import com.example.speerinterviewsubmission.data.model.User
import com.example.speerinterviewsubmission.data.network.Urls
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserServices {
    @GET("/${Urls.USERSEARCH}/{username}")
    suspend fun getSearchUser(@Path("username") userName: String): Response<User>

    @GET("/${Urls.USERSEARCH}/{username}/followers")
    suspend fun getFollowers(@Path("username") userName: String): Response<ArrayList<User>>

    @GET("/${Urls.USERSEARCH}/{username}/following")
    suspend fun getFollowing(@Path("username") userName: String): Response<ArrayList<User>>

}