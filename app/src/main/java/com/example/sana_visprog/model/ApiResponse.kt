package com.example.sana_visprog.model

data class ApiResponse<T>(
    val data: T? = null,
    val error: String? = null
)