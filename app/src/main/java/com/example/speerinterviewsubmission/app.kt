package com.example.speerinterviewsubmission

import android.app.Application
import com.example.speerinterviewsubmission.data.repos.UserRepo
import com.example.speerinterviewsubmission.ui.fragment.user_search.FragmentSearchUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.startKoin
import org.koin.dsl.module

class app : Application() {

    val module = module {
        single { UserRepo() }
        viewModel { FragmentSearchUserViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(module)
        }
    }
}