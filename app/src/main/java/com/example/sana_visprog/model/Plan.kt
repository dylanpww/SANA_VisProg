package com.example.sana_visprog.model

data class CreatePlanRequest(
    val name: String,
    val description: String?
)

data class AddDestinationRequest(
    val destinationId: Int
)

data class UpdateStatusRequest(
    val isVisited: Boolean
)

data class Plan(
    val id: Int,
    val name: String,
    val description: String?,
    val createdAt: String,
//    val destinations: List<DestinationPlanItem>? = null
)

//data class DestinationPlanItem(
//    val id: Int,
//    val isVisited: Boolean,
//    val planId: Int,
//    val destinationId: Int,
//    val destination: DestinationModel?
//)

