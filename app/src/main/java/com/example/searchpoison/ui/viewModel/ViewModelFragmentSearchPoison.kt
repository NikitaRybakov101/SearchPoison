package com.example.searchpoison.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchpoison.repository.dataSourse.NOT_FOUND
import com.example.searchpoison.ui.viewModel.interfacesViewModel.InterfaceViewModelFragmentSearchPoison
import com.example.searchpoison.repository.dataSourse.LOADING
import com.example.searchpoison.repository.RetrofitImpl
import com.example.searchpoison.ui.viewModel.dataSourse.SendData
import com.example.searchpoison.ui.viewModel.dataSourse.StateData
import kotlinx.coroutines.*

class ViewModelFragmentSearchPoison(private val retrofit: RetrofitImpl) : ViewModel() ,
    InterfaceViewModelFragmentSearchPoison {

    private val liveData = MutableLiveData<StateData>()
    override fun getLiveData() = liveData

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getListNews(sendData : SendData) {

        val retrofit = retrofit.getRetrofit()
        liveData.value = StateData.Loading(LOADING)

        scope.launch {
            runCatching {

                val response = retrofit.getListDrug("шанс").execute()

                response
            }.onSuccess { response ->

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        liveData.value = StateData.Success(response.body()!!)
                    } else {
                        liveData.value = StateData.Error(Throwable(NOT_FOUND))
                    }
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    liveData.value = StateData.Error(Throwable(it.message))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}