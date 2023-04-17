package com.example.searchpoison.ui.viewModel.interfacesViewModel

import com.example.searchpoison.ui.viewModel.dataSourse.StateData
import kotlinx.coroutines.flow.Flow

interface InterfaceViewModelFragmentDetailsPoison {
    suspend fun getPoisonFlow(idPoison : String) : Flow<StateData>
}