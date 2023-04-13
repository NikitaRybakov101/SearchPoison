package com.example.searchpoison.ui.viewModel.interfacesViewModel

import androidx.lifecycle.MutableLiveData
import com.example.searchpoison.ui.viewModel.dataSourse.SendData
import com.example.searchpoison.ui.viewModel.dataSourse.StateData

interface InterfaceViewModelFragmentSearchPoison {
    fun getLiveData() : MutableLiveData<StateData>
    fun getListNews(sendData : SendData)
}