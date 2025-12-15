package com.example.sana_visprog.model

data class AuthResponse(
    val data: TokenData
)
data class TokenData(
    val token: String
)