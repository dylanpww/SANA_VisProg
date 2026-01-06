package com.example.sana_visprog.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sana_visprog.SANAVisProgApplication
import com.example.sana_visprog.repository.AuthRepository
import com.example.sana_visprog.utils.JwtUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegisterViewModel (private val repository: AuthRepository) : ViewModel() {
    var usernameInput by mutableStateOf("")
        private set
    var emailInput by mutableStateOf("")
        private set
    var passwordInput by mutableStateOf("")
        private set

    private val _registerState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val registerState = _registerState.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        usernameInput = newUsername
    }
    fun onEmailChange(newEmail: String) {
        emailInput = newEmail
    }
    fun onPasswordChange(newPassword: String) {
        passwordInput = newPassword
    }

    fun register() {
        if (usernameInput.isBlank() || emailInput.isBlank() || passwordInput.isBlank()) {
            _registerState.value = AuthUiState.Error("Username, Email, dan Password tidak boleh kosong")
            return
        }

        viewModelScope.launch {
            _registerState.value = AuthUiState.Loading
            try {
                val response = repository.registerUser(usernameInput, emailInput, passwordInput)
                val token = response.data.token
                val username = JwtUtils.decodeUsername(token)
                _registerState.value = AuthUiState.Success(token, username)

            } catch (e: HttpException) {
                _registerState.value = AuthUiState.Error("Gagal Register: ${e.message()}")
            } catch (e: IOException) {
                _registerState.value = AuthUiState.Error("Koneksi Error. Cek internet/IP Address.")
            } catch (e: Exception) {
                _registerState.value = AuthUiState.Error("Error: ${e.message}")
            }
        }
    }

    fun resetState() {
        _registerState.value = AuthUiState.Idle
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SANAVisProgApplication)
                RegisterViewModel(
                    repository = application.container.authRepository
                )
            }
        }
    }
}