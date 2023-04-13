package com.example.searchpoison.ui.fragments.recycler

import com.example.searchpoison.repository.dataSourse.ResponsePoison

interface InterfaceRecycler {
    fun clearListNews()
    fun updateList(listNews: ArrayList<ResponsePoison>)
}