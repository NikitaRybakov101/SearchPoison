package com.example.searchpoison.repository.repositoryImpl

import com.example.searchpoison.repository.dataSourse.Poison

interface InterfaceRepository {
    fun getListPoison(search : String) : List<Poison>?
}