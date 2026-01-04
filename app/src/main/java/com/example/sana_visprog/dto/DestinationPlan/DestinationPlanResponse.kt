package com.example.sana_visprog.dto.DestinationPlan

import com.example.sana_visprog.model.PlanDestination
import com.google.gson.annotations.SerializedName

data class PlanDestinationResponse(
    @SerializedName("id")
    val destinationId: Int,
    @SerializedName("destinationPlanId")
    val destinationPlanId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("rating")
    val rating: Float,
    @SerializedName("pictureUrl")
    val picture: String?,
    @SerializedName("isVisited")
    val isVisited: Boolean?
)

fun PlanDestinationResponse.toDomain(): PlanDestination {
    return PlanDestination(
        id = destinationPlanId,
        destinationId = destinationId,
        name = name,
        location = location,
        picture = picture ?: "",
        isVisited = isVisited ?: false
    )
}