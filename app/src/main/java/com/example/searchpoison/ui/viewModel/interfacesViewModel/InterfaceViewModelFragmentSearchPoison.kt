package com.example.searchpoison.ui.viewModel.interfacesViewModel

import androidx.paging.PagingData
import com.example.searchpoison.repository.dataSourse.Poison
import kotlinx.coroutines.flow.StateFlow

interface InterfaceViewModelFragmentSearchPoison {
    fun setQuery(query: String)
    fun getPageFlow() : StateFlow<PagingData<Poison>>
}