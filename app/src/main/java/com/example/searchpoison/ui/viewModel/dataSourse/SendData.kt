package com.example.searchpoison.ui.viewModel.dataSourse

sealed class SendData {

    data class SendParameterCountryNews(
        val country : String,
        val category : String
    ) : SendData()

    data class SendParameterNews(
        val query : String,
        val from : String,
        val to : String,
        val sortBy : String,
    ) : SendData()

}
