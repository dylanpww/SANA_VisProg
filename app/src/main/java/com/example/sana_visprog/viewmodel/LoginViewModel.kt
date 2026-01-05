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
import com.example.sana_visprog.repository.UserPreferences
import com.example.sana_visprog.utils.JwtUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class LoginViewModel(private val repository: AuthRepository,
    private val userPreferences: UserPreferences) : ViewModel() {
    var emailInput by mutableStateOf("")
        private set
    var passwordInput by mutableStateOf("")
        private set

    private val _loginState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val loginState = _loginState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        emailInput = newEmail
    }
    fun onPasswordChange(newPassword: String) {
        passwordInput = newPassword
    }

    fun login() {
        if (emailInput.isBlank() || passwordInput.isBlank()) {
            _loginState.value = AuthUiState.Error("Email dan Password tidak boleh kosong")
            return
        }

        viewModelScope.launch {
            _loginState.value = AuthUiState.Loading
            try {
                val response = repository.loginUser(emailInput, passwordInput)
                val token = response.data.token
                val username = JwtUtils.decodeUsername(token)
                userPreferences.saveSession(token, username)
                _loginState.value = AuthUiState.Success(token, username)

            } catch (e: HttpException) {
                _loginState.value = AuthUiState.Error("Gagal Login: Email/Password Salah")
            } catch (e: IOException) {
                _loginState.value = AuthUiState.Error("Koneksi Error. Cek internet/IP Address.")
            } catch (e: Exception) {
                _loginState.value = AuthUiState.Error("Error: ${e.message}")
            }
        }
    }

    fun resetState() {
        _loginState.value = AuthUiState.Idle
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SANAVisProgApplication)
                val repo = application.container.authRepository
                LoginViewModel(repository = repo,
                    userPreferences = application.container.userPreferences)
            }
        }
    }
}