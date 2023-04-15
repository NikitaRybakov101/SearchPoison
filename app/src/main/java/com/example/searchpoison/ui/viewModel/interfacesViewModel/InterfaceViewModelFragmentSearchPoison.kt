package com.example.searchpoison.ui.viewModel.interfacesViewModel

import androidx.lifecycle.MutableLiveData
import com.example.searchpoison.repository.dataSourse.Poison
import com.example.searchpoison.ui.viewModel.dataSourse.StateData
import kotlinx.coroutines.flow.Flow

interface InterfaceViewModelFragmentSearchPoison {
    fun setQuery(query: String)
}