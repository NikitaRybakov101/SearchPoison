package com.example.searchpoison.repository.dataSourse

data class Poison(
    val id: String,
    val imageUri: String,
    val name: String,
    val description: String,
    val categories: Categories
)

data class Categories(
    val iconUrl: String,
    val imageUBackUrl: String
)