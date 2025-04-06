package com.example.speerinterviewsubmission

import android.app.Application
import com.example.speerinterviewsubmission.data.repos.UserRepo
import com.example.speerinterviewsubmission.ui.fragment.follower_following_list.FragmentFollowerFollowingListViewModel
import com.example.speerinterviewsubmission.ui.fragment.profile_view.FragmentFollowerFollowingProfileViewViewModel
import com.example.speerinterviewsubmission.ui.fragment.user_search.FragmentSearchUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class app : Application() {

    val module = module {
        factory { UserRepo() }
        viewModel { FragmentSearchUserViewModel(get()) }
        viewModel { FragmentFollowerFollowingListViewModel(get()) }
        viewModel { FragmentFollowerFollowingProfileViewViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(module)
        }
    }
}