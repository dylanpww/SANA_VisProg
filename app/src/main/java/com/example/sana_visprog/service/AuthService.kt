package com.example.sana_visprog.service

import com.example.sana_visprog.dto.User.LoginRequest
import com.example.sana_visprog.dto.User.RegisterRequest
import com.example.sana_visprog.model.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse
}