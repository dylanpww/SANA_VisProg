package com.example.sana_visprog.dto.Plan

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