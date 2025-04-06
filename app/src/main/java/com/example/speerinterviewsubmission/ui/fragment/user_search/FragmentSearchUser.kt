package com.example.speerinterviewsubmission.ui.fragment.user_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.speerinterviewsubmission.R
import com.example.speerinterviewsubmission.data.model.User
import com.example.speerinterviewsubmission.data.network.APIResponse
import com.example.speerinterviewsubmission.databinding.FragmentFragmentSearchUserBinding
import com.example.speerinterviewsubmission.ui.adapter.UserListAdapter
import com.example.speerinterviewsubmission.utils.Const
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentSearchUser : Fragment() {

    private lateinit var binding: FragmentFragmentSearchUserBinding

    private val viewModel: FragmentSearchUserViewModel by viewModel()
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {


        binding = FragmentFragmentSearchUserBinding.inflate(inflater, container, false)

        setView()
        if (viewModel.userLogin != null) {
            searchUser(viewModel.userLogin!!)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.txtProfileUserName.text.isNullOrEmpty()) activity!!.finish()
                    else resetView()


                }

            })
        return binding.root
    }

    private fun setView() {
        binding.searchBarUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    Toast.makeText(context, "Please Type user name", Toast.LENGTH_SHORT).show()
                } else if (query.length < 3) {
                    Toast.makeText(context, "Please Type 3 letters", Toast.LENGTH_SHORT).show()
                } else {
                    searchUser(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length == 0 && binding.txtProfileUserName.text.isNullOrEmpty()) {
                    binding.txtStatusLabel.isVisible = true
                    binding.txtStatusLabel.text = getString(R.string.use_search_bar)
                    binding.imgStatusLabel.isVisible = false
                }
                return true
            }

        })

        if (::loginId.isInitialized) {
            searchUser(loginId)
        }
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
                    binding.txtStatusLabel.isVisible = false
                    binding.imgStatusLabel.isVisible = true
                }


            }
        })
    }


    private fun resetView() {
        userFollowers = 0
        userFollowing = 0
        binding.txtProfileName.text = ""
        binding.txtProfileUserName.text = ""
        binding.txtFollowers.text = ""
        binding.txtFollowing.text = ""
        binding.txtProfileDescription.text = ""
        binding.txtFollowers.text = "0 Followers"
        binding.txtFollowing.text = "0 Following"

        binding.txtFollowing.isClickable = false
        binding.txtFollowers.isClickable = false

        binding.searchBarUser.setQuery("", false)

        binding.txtStatusLabel.isVisible = true
        binding.profileView.isVisible = false
    }

    private fun setUserView(data: User) {
        binding.txtStatusLabel.isVisible = false
        binding.imgStatusLabel.isVisible = false
        binding.profileView.isVisible = true

        viewModel.userLogin = data.login

        Picasso.with(context).load(data.avatarUrl).fit().into(binding.imgProfilePic)
        if (data.name.isNullOrEmpty()) {
            binding.txtProfileUserName.isVisible = false
            binding.txtProfileName.text = data.login

        } else {
            binding.txtProfileUserName.isVisible = true
            binding.txtProfileName.text = data.name
            binding.txtProfileUserName.text = "@${data.login}"
        }

        if (data.bio.isNullOrEmpty()) {
            binding.txtProfileDescription.isVisible = false
        } else {
            binding.txtProfileDescription.isVisible = true
            binding.txtProfileDescription.text = data.bio
        }
        binding.txtFollowers.text = "${data.followers} Followers"
        binding.txtFollowing.text = "${data.following} Followings"

        binding.txtFollowers.setOnClickListener {
            if (data.followers > 0) {
                val bundle = Bundle()
                bundle.putString(Const.LOGINID, data.login)
                bundle.putString(Const.LISTTAG, "follower")
                bundle.putString(Const.COUNT, "" + data.followers)
                findNavController().navigate(
                    R.id.action_fragmentSearchUser_to_fragmentFollowerFollowingList, bundle
                )
            } else {
                Toast.makeText(context, "No one follow ${loginId}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.txtFollowing.setOnClickListener {
            if (data.following > 0) {
                val bundle = Bundle()
                bundle.putString(Const.LOGINID, data.login)
                bundle.putString(Const.LISTTAG, "following")
                bundle.putString(Const.COUNT, "" + data.following)
                findNavController().navigate(
                    R.id.action_fragmentSearchUser_to_fragmentFollowerFollowingList, bundle
                )
            } else {
                Toast.makeText(context, "${loginId} Doesn't follow anyone", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }


}