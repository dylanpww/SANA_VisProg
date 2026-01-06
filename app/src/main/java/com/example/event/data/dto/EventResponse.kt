package com.example.event.data.dto

data class EventResponse(
    val id: Int,
    val name: String,
    val location: String,
    val description: String,
    val photoUrl: String?,
    val rating: Float,
    val createdAt: String,
    val updatedAt: String
)

data class EventRequest(
    val name: String,
    val location: String,
    val description: String,
    val photoUrl: String? = null,
    val rating: Float = 0f
)
