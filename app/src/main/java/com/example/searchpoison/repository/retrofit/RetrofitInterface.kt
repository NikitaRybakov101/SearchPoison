package com.example.searchpoison.repository.retrofit

import com.example.searchpoison.repository.dataSourse.ResponsePoisonDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("/api/ppp/item/")
    fun getPoison(@Query("id") id : String): Call<ResponsePoisonDto>

    @GET("/api/ppp/index/")
    fun getListPoison(
        @Query("search") search : String,
        @Query("offset") offset : Int,
        @Query("limit") limit : Int

    ): Call<List<ResponsePoisonDto>>

}