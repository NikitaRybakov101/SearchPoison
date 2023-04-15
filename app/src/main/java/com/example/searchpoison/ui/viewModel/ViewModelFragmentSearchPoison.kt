package com.example.searchpoison.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.searchpoison.ui.viewModel.interfacesViewModel.InterfaceViewModelFragmentSearchPoison
import com.example.searchpoison.repository.dataSourse.Poison
import com.example.searchpoison.repository.repositoryImpl.RepositoryImpl
import com.example.searchpoison.repository.pager.PageSourcePoison
import com.example.searchpoison.repository.retrofit.RetrofitImpl
import com.example.searchpoison.ui.fragments.recycler.PagingAdapterRecyclerPoison.Companion.PAGE_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class ViewModelFragmentSearchPoison : ViewModel() , InterfaceViewModelFragmentSearchPoison {

  /*  val listPoisonFlow : StateFlow<PagingData<Poison>> = Pager(PagingConfig(pageSize = MAX_PAGE_SIZE, enablePlaceholders = false)) {
        PoisonPageSource(RepositoryImpl(RetrofitImpl()),"шанс")
    }.flow.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())*/

    private val _query = MutableStateFlow("")
    private val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val listPoisonFlow: StateFlow<PagingData<Poison>> = query
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(query: String): Pager<Int, Poison> {
        return Pager(PagingConfig(PAGE_SIZE, enablePlaceholders = false)) {
             PageSourcePoison(RepositoryImpl(RetrofitImpl()),query)
        }
    }

    override fun setQuery(query: String) {
        _query.tryEmit(query)
    }
}