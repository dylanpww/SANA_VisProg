package com.example.event.data.dto

data class Event(
    val id: Int,
    val name: String,
    val location: String,
    val photoUrl: String,
    val rating: Float,
    val description: String
)
