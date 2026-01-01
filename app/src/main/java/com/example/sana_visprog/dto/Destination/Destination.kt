package com.example.sana_visprog.dto.Destination

import com.example.sana_visprog.model.Destination
import com.google.gson.annotations.SerializedName

data class DestinationResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("rating")
    val rating: Float,

    @SerializedName("pictureUrl")
    val pictureUrl: String,

    @SerializedName("pictureUrl2")
    val pictureUrl2: String,

    @SerializedName("categoryId")
    val categoryId: Int,

    @SerializedName("provinceId")
    val provinceId: Int
)

fun DestinationResponse.toDestination(): Destination {
    return Destination(
        id = id,
        name = name,
        description = description,
        location = location,
        rating = rating,
        pictureUrl = pictureUrl,
        pictureUrl2 = pictureUrl2,
        categoryId = categoryId,
        provinceId = provinceId
    )
}