package com.example.sana_visprog.viewmodel

sealed interface AuthUiState {
    object Idle : AuthUiState
    object Loading : AuthUiState
    data class Success(val token: String, val username: String) : AuthUiState
    data class Error(val message: String) : AuthUiState
}