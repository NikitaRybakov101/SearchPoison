package com.example.searchpoison.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.searchpoison.ui.viewModel.interfacesViewModel.InterfaceViewModelFragmentSearchPoison
import com.example.searchpoison.repository.dataSourse.Poison
import com.example.searchpoison.repository.pager.PageSourcePoison
import com.example.searchpoison.repository.repositoryImpl.InterfaceRepository
import com.example.searchpoison.ui.fragments.recycler.PagingAdapterRecyclerPoison.Companion.PAGE_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class ViewModelFragmentSearchPoison(private val repositoryImpl: InterfaceRepository) : ViewModel() , InterfaceViewModelFragmentSearchPoison {

    private var _query = MutableStateFlow("")
    private val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPoisonsFlow(): StateFlow<PagingData<Poison>> = query

        .map(::newPagerWithQuery)
        .flatMapLatest { pager -> pager.flow }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPagerWithQuery(query: String): Pager<Int, Poison> = Pager(PagingConfig(PAGE_SIZE, enablePlaceholders = false)) {
        PageSourcePoison(repositoryImpl,query)
    }

    override fun setQuery(query: String) {
        _query.tryEmit(query)
    }
}