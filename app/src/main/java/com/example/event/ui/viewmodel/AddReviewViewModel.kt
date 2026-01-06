package com.example.event.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.event.data.container.AppContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddReviewViewModel(private val eventId: Int) : ViewModel() {
    private val reviewService = AppContainer.getReviewService()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    fun createReview(
        userName: String,
        rating: Float,
        comment: String,
        photoUrl: String? = null,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val result = reviewService.createReview(
                    eventId = eventId,
                    userName = userName,
                    rating = rating,
                    comment = comment,
                    photoUrl = photoUrl
                )
                result.onSuccess {
                    _successMessage.value = "Review added successfully"
                    onSuccess()
                }.onFailure { error ->
                    _errorMessage.value = error.message ?: "Failed to create review"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }
}
