package com.example.sana_visprog.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sana_visprog.SANAVisProgApplication
import com.example.sana_visprog.repository.AuthRepository
import com.example.sana_visprog.repository.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    var userName = mutableStateOf("Loading...")
    var isLoggedOut = mutableStateOf(false)

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            val name = userPreferences.userName.first()
            userName.value = name ?: "Guest"
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            isLoggedOut.value = true
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SANAVisProgApplication)
                ProfileViewModel(
                    authRepository = app.container.authRepository,
                    userPreferences = app.container.userPreferences
                )
            }
        }
    }
}