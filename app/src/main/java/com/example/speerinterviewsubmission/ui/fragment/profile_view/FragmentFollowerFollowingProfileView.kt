package com.example.speerinterviewsubmission.ui.fragment.profile_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.speerinterviewsubmission.data.model.User
import com.example.speerinterviewsubmission.data.network.APIResponse
import com.example.speerinterviewsubmission.databinding.FragmentFollowerFollowingProfileViewBinding
import com.example.speerinterviewsubmission.ui.adapter.UserListAdapter
import com.example.speerinterviewsubmission.utils.Const
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentFollowerFollowingProfileView : Fragment() {
    private lateinit var binding: FragmentFollowerFollowingProfileViewBinding

    private val viewModel: FragmentFollowerFollowingProfileViewViewModel by viewModel()
    private lateinit var adapter: UserListAdapter
    private var userFollowers = 0L
    private var userFollowing = 0L

    private lateinit var loginId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            loginId = requireArguments().getString(Const.LOGINID)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerFollowingProfileViewBinding.inflate(inflater, container, false)

        setView()
        searchUser(loginId)

        return binding.root

    }

    private fun setView() {

        adapter = UserListAdapter()
        binding.recUsers.adapter = adapter

//        adapter.itemClickSubject.subscribe{
//            val bundle = Bundle()
//            bundle.putString(LOGINID,it)
//            findNavController().navigate(R.id.action_fragmentSearchUser_self,bundle)
//        }
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
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
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

                    binding.btnFollowers.setOnClickListener { cl ->
                        adapter.updateUsers(it.data)
                        binding.btnFollowers.text = "${it.data.size} Followers"

                    }

                }

                is APIResponse.Error -> {
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
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
                    binding.btnFollowing.setOnClickListener { cl ->
                        adapter.updateUsers(it.data)

                    }

                }

                is APIResponse.Error -> {
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()                }


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

        binding.btnFollowing.isClickable = false
        binding.btnFollowers.isClickable = false
        adapter.updateUsers(ArrayList())


        binding.profileView.isVisible = false
    }

    private fun setUserView(data: User) {
        binding.profileView.isVisible = true

        Picasso.with(context).load(data.avatarUrl).fit().into(binding.imgProfilePic)
        if (data.name.isNullOrEmpty()) {
            binding.txtProfileUserName.isVisible = false
            binding.txtProfileName.text = data.login

        } else {
            binding.txtProfileUserName.isVisible = true
            binding.txtProfileName.text = data.name
            binding.txtProfileUserName.text = "@${data.login}"
        }

        binding.btnFollowers.text = "${data.followers} Followers"
        binding.btnFollowing.text = "${data.following} Followers"

        if (data.bio.isNullOrEmpty()) {
            binding.txtProfileDescription.isVisible = false
        } else {
            binding.txtProfileDescription.isVisible = true
            binding.txtProfileDescription.text = data.bio
        }
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