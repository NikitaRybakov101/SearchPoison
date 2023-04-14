package com.example.searchpoison.repository.repositoryImpl

import com.example.searchpoison.repository.dataSourse.Poison
import com.example.searchpoison.repository.dataSourse.ResponsePoison
import com.example.searchpoison.repository.dataSourse.toPoison
import com.example.searchpoison.repository.retrofit.RetrofitImpl

class RepositoryImpl(retrofit: RetrofitImpl) : InterfaceRepository {

    private val retrofitInterface = retrofit.getRetrofit()

    override fun getListPoison(search: String) : List<Poison>? {
        val responseListPoison = retrofitInterface.getListPoison(search).execute()

        return if (responseListPoison.isSuccessful && responseListPoison.body() != null) {
            mapResponseListPoison(responseListPoison.body()!!)
        } else {
            null
        }
    }

    private fun mapResponseListPoison(responseListPoison: List<ResponsePoison>): List<Poison> {
        val listPoison = mutableListOf<Poison>()

        responseListPoison.forEach { responsePoison ->
            listPoison.add(responsePoison.toPoison())
        }

        return listPoison
    }
}