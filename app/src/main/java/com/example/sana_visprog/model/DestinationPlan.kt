package com.example.sana_visprog.model

data class PlanDestination(
    val id: Int,
    val destinationId: Int,
    val name: String,
    val location: String,
    val picture: String,
    val isVisited: Boolean
)