package com.example.sana_visprog.repository

import com.example.sana_visprog.dto.User.LoginRequest
import com.example.sana_visprog.dto.User.RegisterRequest
import com.example.sana_visprog.model.AuthResponse
import com.example.sana_visprog.service.AuthService
import com.example.sana_visprog.utils.JwtUtils

class AuthRepository(
    private val apiService: AuthService,
    private val userPreferences: UserPreferences
) {

    suspend fun loginUser(email: String, password: String): AuthResponse {
        val request = LoginRequest(email, password)
        val response = apiService.login(request)

        if (response.data?.token != null) {
            val token = response.data.token

            var username = JwtUtils.decodeUsername(token)

            if (username.isEmpty()) {
                username = "SANA User"
            }

            userPreferences.saveSession(token, username)
        }

        return response
    }

    suspend fun registerUser(username: String, email: String, password: String): AuthResponse {
        val request = RegisterRequest(username, email, password)
        return apiService.register(request)
    }

    suspend fun logout() {
        userPreferences.logout()
    }
}
