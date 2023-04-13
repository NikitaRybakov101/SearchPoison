package com.example.searchpoison.repository.interfecesRepository

import com.example.searchpoison.repository.dataSourse.ResponsePoison
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("/api/ppp/item/")
    fun getDrug(@Query("id") id : String): Call<List<ResponsePoison>>

    @GET("/api/ppp/index/")
    fun getListDrug(@Query("search") search : String): Call<List<ResponsePoison>>
}