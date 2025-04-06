package com.example.speerinterviewsubmission.ui.fragment.user_search

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.speerinterviewsubmission.R
import com.example.speerinterviewsubmission.data.model.User
import com.example.speerinterviewsubmission.data.network.APIResponse
import com.example.speerinterviewsubmission.databinding.FragmentFragmentSearchUserBinding
import com.example.speerinterviewsubmission.ui.adapter.UserListAdapter
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class FragmentSearchUser : Fragment() {

    private lateinit var binding: FragmentFragmentSearchUserBinding

    private val viewModel: FragmentSearchUserViewModel by viewModel()
    private lateinit var adapter: UserListAdapter
    private var userFollowers = 0L
    private var userFollowing = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentFragmentSearchUserBinding.inflate(inflater, container, false)

        setView()
        return binding.root
    }

    private fun setView() {
        adapter = UserListAdapter()
        binding.recUsers.adapter = adapter

        binding.btnFollowing.setOnClickListener {
            if(userFollowing == 0L ) adapter.updateUsers(ArrayList())
        }

        binding.btnFollowers.setOnClickListener {
            if(userFollowers == 0L) adapter.updateUsers(ArrayList())
        }

        binding.searchBarUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    Toast.makeText(context, "Please Type user name", Toast.LENGTH_SHORT).show()
                } else if (query.length < 3) {
                    Toast.makeText(context, "Please Type 3 letters", Toast.LENGTH_SHORT).show()
                } else {
                    adapter.updateUsers(ArrayList())
                    searchUser(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText?.length == 0) binding.txtStatusLabel.text =
                    getString(R.string.use_search_bar)
                return true
            }

        })
    }

    private fun searchUser(query: String) {
        resetView()
        viewModel.searchUser(query).observe(this, Observer {
            when (it) {
                is APIResponse.Loading -> {}
                is APIResponse.Success -> {
                    setUserView(it.data)
                }

                is APIResponse.Error -> {
                    binding.profileView.isVisible = false
                    binding.txtStatusLabel.isVisible = true
                    binding.txtStatusLabel.text = it.message
                }


            }
        })
    }

    private fun getFollowers(loginId: String) {
        viewModel.getFollowers(loginId).observe(this, Observer {
            when (it) {
                is APIResponse.Loading -> {}
                is APIResponse.Success -> {
                    binding.btnFollowers.isClickable = false
                    adapter.updateUsers(it.data)

                    binding.btnFollowers.setOnClickListener {cl ->
                        adapter.updateUsers(it.data)
                    }

                }

                is APIResponse.Error -> {
                    binding.txtStatusLabel.text = it.message
                }


            }
        })
    }

    private fun getFollowings(loginid: String) {
        viewModel.getFollowings(loginid).observe(this, Observer {
            when (it) {
                is APIResponse.Loading -> {}
                is APIResponse.Success -> {
                    binding.btnFollowing.isClickable = false
                    binding.btnFollowing.setOnClickListener {cl ->
                        adapter.updateUsers(it.data)
                    }

                }

                is APIResponse.Error -> {
                    binding.txtStatusLabel.text = it.message
                }


            }
        })
    }

    private fun resetView() {
        userFollowers = 0
        userFollowing = 0
        binding.txtProfileName.text = ""
        binding.txtProfileUserName.text = ""
        binding.btnFollowers.text = ""
        binding.btnFollowing.text = ""
        binding.txtProfileDescription.text = ""
        binding.btnFollowers.text = "0 Followers"
        binding.btnFollowing.text = "0 Following"
    }

    private fun setUserView(data: User) {
        binding.txtStatusLabel.isVisible = false
        binding.profileView.isVisible = true

        Picasso.with(context).load(data.avatarUrl).fit().into(binding.imgProfilePic)
        if (data.name.isNullOrEmpty()){
            binding.txtProfileUserName.isVisible =false
            binding.txtProfileName.text = data.login

        }else{
            binding.txtProfileUserName.isVisible =true
            binding.txtProfileName.text = data.name
            binding.txtProfileUserName.text = data.login
        }

        if (data.bio.isNullOrEmpty()){
            binding.txtProfileDescription.isVisible = false
        }
        else{
            binding.txtProfileDescription.isVisible = true
            binding.txtProfileDescription.text = data.bio
        }
        binding.btnFollowers.text = "${data.followers} Followers"
        binding.btnFollowing.text = "${data.following}Followings"

        if (data.followers > 0) {
            userFollowers = data.followers
            getFollowers(data.login)
        }
        if (data.following > 0) {
            userFollowing = data.following
            getFollowings(data.login)
        }

    }
}