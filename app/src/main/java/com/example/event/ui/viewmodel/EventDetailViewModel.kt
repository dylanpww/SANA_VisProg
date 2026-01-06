package com.example.event.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.event.data.container.AppContainer
import com.example.event.data.dto.Event
import com.example.event.data.dto.Review
import com.example.event.ui.model.DummyEventData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventDetailViewModel(private val eventId: Int = 1) : ViewModel() {
    private val eventService = AppContainer.getEventService()
    private val reviewService = AppContainer.getReviewService()

    private val _event = MutableStateFlow(DummyEventData.eventDetail)
    val event: StateFlow<Event> = _event.asStateFlow()

    private val _reviews = MutableStateFlow(DummyEventData.reviews)
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadEventDetails()
        loadReviews()
    }

    private fun loadEventDetails() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = eventService.getEventById(eventId)
                result.onSuccess { event ->
                    _event.value = event
                }.onFailure { error ->
                    _errorMessage.value = error.message ?: "Failed to load event details"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadReviews() {
        viewModelScope.launch {
            try {
                val result = reviewService.getReviewsByEventId(eventId)
                result.onSuccess { reviews ->
                    _reviews.value = reviews
                }.onFailure { error ->
                    _errorMessage.value = error.message ?: "Failed to load reviews"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error loading reviews: ${e.message}"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

