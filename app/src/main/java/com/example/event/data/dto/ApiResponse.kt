package com.example.event.data.dto

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String?
)
