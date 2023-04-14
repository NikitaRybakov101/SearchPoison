package com.example.searchpoison.ui.viewModel.dataSourse

import com.example.searchpoison.repository.dataSourse.Poison

sealed class StateData {
    data class Success(val listPoisons : List<Poison>) : StateData()
    data class Loading(val loading : String) : StateData()
    data class Error  (val error : Throwable) : StateData()
}
