package com.example.searchpoison.repository.dataSourse

import com.google.gson.annotations.SerializedName

data class ResponsePoison (
    @field:SerializedName("id") val id: String?,
    @field:SerializedName("image") val imageUri: String?,
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("description") val description: String?,
)










