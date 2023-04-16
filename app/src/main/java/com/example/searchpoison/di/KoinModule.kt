package com.example.searchpoison.di

import com.example.searchpoison.repository.repositoryImpl.InterfaceRepository
import com.example.searchpoison.repository.repositoryImpl.RepositoryImpl
import com.example.searchpoison.repository.retrofit.RetrofitImpl
import com.example.searchpoison.repository.retrofit.InterfaceRetrofit
import com.example.searchpoison.ui.viewModel.ViewModelFragmentDetailsPoison
import com.example.searchpoison.ui.viewModel.ViewModelFragmentSearchPoison
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {

    single<InterfaceRetrofit> {
        RetrofitImpl().getRetrofit()
    }

    single<InterfaceRepository> {
        RepositoryImpl(retrofitInterface = get())
    }
}

val viewModelModule = module {
    viewModel(named(VIEW_MODEL_SEARCH)) {
        ViewModelFragmentSearchPoison(repositoryImpl = get())
    }

    viewModel(named(VIEW_MODEL_DETAILS)) {
        ViewModelFragmentDetailsPoison(repositoryImpl = get())
    }
}


