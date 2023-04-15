package com.example.searchpoison.repository.repositoryImpl

import com.example.searchpoison.repository.dataSourse.Poison
import com.example.searchpoison.repository.retrofit.RetrofitImpl
import com.example.searchpoison.utils.toPoison

class RepositoryImpl(retrofit: RetrofitImpl) : InterfaceRepository {

    private val retrofitInterface = retrofit.getRetrofit()

    override fun getListPoison(search: String, offset: Int, pageSize: Int) : List<Poison>? {
        val responseListPoison = retrofitInterface.getListPoison(search,offset,pageSize).execute()

        return if (responseListPoison.isSuccessful && responseListPoison.body() != null) {
            checkNotNull(responseListPoison.body()).map { it.toPoison() }
        } else {
            null
        }
    }

    override fun getPoison(idPoison: String) : Poison? {
        val responsePoison = retrofitInterface.getPoison(idPoison).execute()

        return if (responsePoison.isSuccessful && responsePoison.body() != null) {
            checkNotNull(responsePoison.body()).toPoison()
        } else {
            null
        }
    }

}