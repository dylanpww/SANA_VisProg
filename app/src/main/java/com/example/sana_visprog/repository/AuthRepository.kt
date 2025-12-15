package com.example.sana_visprog.repository

import com.example.sana_visprog.dto.User.LoginRequest
import com.example.sana_visprog.service.AuthService

class AuthRepository(private val api: AuthService) {
    suspend fun loginUser(req: LoginRequest) = api.login(req)
}