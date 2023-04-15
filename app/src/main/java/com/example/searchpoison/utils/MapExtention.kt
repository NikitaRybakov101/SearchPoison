package com.example.searchpoison.utils

import com.example.searchpoison.repository.dataSourse.Categories
import com.example.searchpoison.repository.dataSourse.Poison
import com.example.searchpoison.repository.dataSourse.ResponsePoisonDto

fun ResponsePoisonDto.toPoison() : Poison {

    val categories = if (this.categories == null) {
        Categories("null","null")
    } else {
        Categories(
            categories.iconUrl ?: "null",
            categories.imageBackUrl ?: "null"
        )
    }

    return Poison(
        this.id ?: "",
        this.imageUri ?: "",
        this.name ?: "",
        this.description ?: "",
        categories
    )
}