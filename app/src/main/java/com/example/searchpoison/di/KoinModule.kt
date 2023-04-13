package com.example.searchpoison.di

import com.example.searchpoison.repository.RetrofitImpl
import com.example.searchpoison.ui.viewModel.ViewModelFragmentSearchPoison
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {

    single(named(RETROFIT_IMPL)) {
        RetrofitImpl()
    }
}

val viewModelModule = module {
    viewModel(named(VIEW_MODEL)) { (retrofit: RetrofitImpl) ->
        ViewModelFragmentSearchPoison(retrofit)
    }
}


