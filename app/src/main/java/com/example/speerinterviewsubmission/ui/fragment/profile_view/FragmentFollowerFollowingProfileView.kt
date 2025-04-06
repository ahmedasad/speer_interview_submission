package com.example.speerinterviewsubmission.ui.fragment.profile_view

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.speerinterviewsubmission.R

class FragmentFollowerFollowingProfileView : Fragment() {

    companion object {
        fun newInstance() = FragmentFollowerFollowingProfileView()
    }

    private val viewModel: FragmentFollowerFollowingProfileViewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_fragment__follower_following_profile_view,
            container,
            false
        )
    }
}