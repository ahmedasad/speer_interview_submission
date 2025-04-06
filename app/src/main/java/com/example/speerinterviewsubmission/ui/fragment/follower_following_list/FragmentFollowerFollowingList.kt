package com.example.speerinterviewsubmission.ui.fragment.follower_following_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.speerinterviewsubmission.R
import com.example.speerinterviewsubmission.data.network.APIResponse
import com.example.speerinterviewsubmission.databinding.FragmentFragmentFollowerFollowingListBinding
import com.example.speerinterviewsubmission.ui.adapter.UserListAdapter
import com.example.speerinterviewsubmission.utils.Const
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentFollowerFollowingList : Fragment() {


    private lateinit var binding: FragmentFragmentFollowerFollowingListBinding
    private val viewModel: FragmentFollowerFollowingListViewModel by viewModel()
    private lateinit var adapter: UserListAdapter


    private lateinit var loginId: String
    private lateinit var count: String
    private lateinit var listTag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            loginId = requireArguments().getString(Const.LOGINID)!!
            listTag = requireArguments().getString(Const.LISTTAG)!!
            count = requireArguments().getString(Const.COUNT)!!
        }
        if(viewModel.listTag.isNullOrEmpty()){
            viewModel.userLogin = loginId
            viewModel.count = count
            viewModel.listTag = listTag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFragmentFollowerFollowingListBinding.inflate(inflater, container, false)


        setView()
        return binding.root

    }

    private fun setView() {
        adapter = UserListAdapter()
        binding.recUsers.adapter = adapter

        adapter.itemClickSubject.subscribe{
            val bundle = Bundle()
            bundle.putString(Const.LOGINID,it)
            findNavController().navigate(R.id.action_fragmentFollowerFollowingList_to_fragmentFollowerFollowingProfileView,bundle)
        }

        binding.txtUserName.text = loginId
        if (listTag == "follower") {
            binding.txtCount.text = "${count} Followers"
            getFollowers(viewModel.userLogin!!)
        } else {
            binding.txtCount.text = "${count} Following"
            getFollowings(viewModel.userLogin!!)
        }

    }

    private fun getFollowers(loginId: String) {
        viewModel.getFollowers(loginId).observe(this, Observer {
            when (it) {
                is APIResponse.Loading -> {}
                is APIResponse.Success -> {
                    adapter.updateUsers(it.data)

                    viewModel.userLogin = loginId
                    viewModel.listTag = listTag

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
                        adapter.updateUsers(it.data)

                    viewModel.userLogin = loginId
                    viewModel.listTag = listTag
                }
                is APIResponse.Error -> {
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                }


            }
        })
    }
}