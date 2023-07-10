package com.venicio.gitfinder.di

import com.venicio.gitfinder.data.repository.Repository
import com.venicio.gitfinder.viewmodel.GitFinderViewModel
import com.venicio.gitfinder.viewmodel.SingleUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (rp: Repository) ->  GitFinderViewModel(rp) }
    viewModel { (rp: Repository) ->  SingleUserViewModel(rp) }
}