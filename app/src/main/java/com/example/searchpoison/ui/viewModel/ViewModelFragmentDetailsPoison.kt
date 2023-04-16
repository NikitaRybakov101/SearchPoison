package com.example.searchpoison.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchpoison.repository.repositoryImpl.RepositoryImpl
import com.example.searchpoison.repository.dataSourse.NOT_FOUND
import com.example.searchpoison.ui.viewModel.interfacesViewModel.InterfaceViewModelFragmentSearchPoison
import com.example.searchpoison.repository.dataSourse.LOADING
import com.example.searchpoison.repository.repositoryImpl.InterfaceRepository
import com.example.searchpoison.ui.viewModel.dataSourse.StateData
import com.example.searchpoison.ui.viewModel.interfacesViewModel.InterfaceViewModelFragmentDetailsPoison
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class ViewModelFragmentDetailsPoison(private val repositoryImpl: InterfaceRepository) : ViewModel() , InterfaceViewModelFragmentDetailsPoison {

    override fun getPoisonFlow(idPoison : String) : Flow<StateData> = flow {

        emit(StateData.Loading(LOADING))

        runCatching {
            withContext(Dispatchers.IO) {
                repositoryImpl.getPoison(idPoison)
            }
        }.onSuccess { poison ->

            if (poison != null) {
                emit(StateData.SuccessItemPoison(poison))
            } else {
                emit(StateData.Error(Throwable(NOT_FOUND)))
            }
        }.onFailure {
            emit(StateData.Error(Throwable(it.message)))
        }
    }
}