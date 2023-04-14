package com.example.searchpoison.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchpoison.repository.repositoryImpl.RepositoryImpl
import com.example.searchpoison.repository.dataSourse.NOT_FOUND
import com.example.searchpoison.ui.viewModel.interfacesViewModel.InterfaceViewModelFragmentSearchPoison
import com.example.searchpoison.repository.dataSourse.LOADING
import com.example.searchpoison.ui.viewModel.dataSourse.StateData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class ViewModelFragmentSearchPoison(private val repositoryImpl: RepositoryImpl) : ViewModel() , InterfaceViewModelFragmentSearchPoison {

    override fun getListPoisonFlow(search : String) : Flow<StateData> = flow {

        emit(StateData.Loading(LOADING))

        runCatching {
            withContext(Dispatchers.IO) {
                repositoryImpl.getListPoison(search)
            }
        }.onSuccess { listPoison ->

            if (listPoison != null) {
                emit(StateData.Success(listPoison))
            } else {
                emit(StateData.Error(Throwable(NOT_FOUND)))
            }
        }.onFailure {
            emit(StateData.Error(Throwable(it.message)))
        }
    }
}