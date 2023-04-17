package com.example.searchpoison.repository.repositoryImpl

import com.example.searchpoison.repository.dataSourse.Poison

interface InterfaceRepository {
   suspend fun getListPoison(search: String, offset: Int, pageSize: Int) : List<Poison>?
   suspend fun getPoison(idPoison: String) : Poison?
}