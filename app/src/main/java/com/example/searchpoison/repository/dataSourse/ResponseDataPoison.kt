package com.example.searchpoison.repository.dataSourse

import com.google.gson.annotations.SerializedName

fun ResponsePoison.toPoison() = Poison(
    this.id ?: "",
    this.imageUri ?: "",
    this.name ?: "",
    this.description ?: ""
)
data class ResponsePoison (
    @field:SerializedName("id") val id: String?,
    @field:SerializedName("image") val imageUri: String?,
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("description") val description: String?,
)










