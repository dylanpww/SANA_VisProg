package com.example.sana_visprog.repository

import com.example.sana_visprog.dto.User.LoginRequest
import com.example.sana_visprog.dto.User.RegisterRequest
import com.example.sana_visprog.model.AuthResponse
import com.example.sana_visprog.service.AuthService

class AuthRepository(private val apiService: AuthService) {

    suspend fun loginUser(email: String, password: String): AuthResponse {
        val request = LoginRequest(email, password)
        return apiService.login(request)
    }

    suspend fun registerUser(username: String, email: String, password: String): AuthResponse {
        val request = RegisterRequest(username, email, password)
        return apiService.register(request)
    }
}