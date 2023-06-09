package com.example.searchpoison.repository.retrofit

import com.example.searchpoison.repository.dataSourse.BASE_URL_API
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl {
    fun getRetrofit() : InterfaceRetrofit {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return retrofit.create(InterfaceRetrofit::class.java)
    }
}