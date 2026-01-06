package com.example.event.data.dto

data class ReviewResponse(
    val id: Int,
    val eventId: Int,
    val userName: String,
    val rating: Float,
    val comment: String,
    val photoUrl: String?,
    val createdAt: String,
    val updatedAt: String
)

data class ReviewRequest(
    val eventId: Int,
    val userName: String,
    val rating: Float,
    val comment: String,
    val photoUrl: String? = null
)
