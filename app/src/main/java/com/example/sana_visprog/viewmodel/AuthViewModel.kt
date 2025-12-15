package com.example.sana_visprog.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sana_visprog.repository.AuthRepository
import com.example.sana_visprog.utils.JwtUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Definisikan status UI untuk testing
sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    data class Success(val token: String, val username: String) : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    // State untuk input text (Email & Password)
    var emailInput by mutableStateOf("")
        private set

    var passwordInput by mutableStateOf("")
        private set

    // State untuk status Login (Loading/Success/Error)
    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState = _loginState.asStateFlow()

    // Fungsi untuk mengubah text input
    fun onEmailChange(newEmail: String) {
        emailInput = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        passwordInput = newPassword
    }

    // Fungsi Login
    fun login() {
        if (emailInput.isBlank() || passwordInput.isBlank()) {
            _loginState.value = LoginUiState.Error("Email dan Password tidak boleh kosong")
            return
        }

        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading
            try {
                // Panggil Repository
                val response = repository.loginUser(emailInput, passwordInput)
                val token = response.data.token

                // Decode token untuk ambil username (Opsional, buat test saja)
                // Jika belum ada JwtUtils, hapus baris ini dan ganti username jadi string biasa
                val username = JwtUtils.decodeUsername(token)

                _loginState.value = LoginUiState.Success(token, username)

            } catch (e: HttpException) {
                _loginState.value = LoginUiState.Error("Gagal Login: ${e.response()?.errorBody()?.string() ?: e.message}")
            } catch (e: IOException) {
                _loginState.value = LoginUiState.Error("Koneksi Error. Cek internet/IP Address.")
            } catch (e: Exception) {
                _loginState.value = LoginUiState.Error("Error: ${e.message}")
            }
        }
    }

    // Reset state supaya Toast tidak muncul terus menerus saat recompose
    fun resetState() {
        _loginState.value = LoginUiState.Idle
    }
}