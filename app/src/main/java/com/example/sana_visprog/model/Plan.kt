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
    val destinations: List<PlanDestination>
)

