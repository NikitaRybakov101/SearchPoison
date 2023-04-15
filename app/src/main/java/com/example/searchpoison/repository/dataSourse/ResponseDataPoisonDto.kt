package com.example.searchpoison.repository.dataSourse

import com.google.gson.annotations.SerializedName

data class ResponsePoisonDto (
    @field:SerializedName("id") val id: String?,
    @field:SerializedName("image") val imageUri: String?,
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("description") val description: String?,
    @field:SerializedName("categories") val categories: ResponseCategoriesDto?,
)

data class ResponseCategoriesDto (
    @field:SerializedName("icon") val iconUrl: String?,
    @field:SerializedName("image") val imageBackUrl: String?
)


