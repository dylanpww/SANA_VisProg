package com.example.sana_visprog.dto.Destination

import com.google.gson.annotations.SerializedName

data class CreateDestinationRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("rating")
    val rating: Float = 0.0f,

    @SerializedName("pictureUrl")
    val pictureUrl: String,

    @SerializedName("pictureUrl2")
    val pictureUrl2: String,

    @SerializedName("categoryId")
    val categoryId: Int,

    @SerializedName("provinceId")
    val provinceId: Int
)