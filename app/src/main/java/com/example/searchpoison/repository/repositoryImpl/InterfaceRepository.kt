package com.example.searchpoison.repository.repositoryImpl

import com.example.searchpoison.repository.dataSourse.Poison

interface InterfaceRepository {
   fun getListPoison(search: String, offset: Int, pageSize: Int) : List<Poison>?
   fun getPoison(idPoison: String) : Poison?
}