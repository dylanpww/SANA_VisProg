package com.example.event.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.event.data.container.AppContainer
import com.example.event.data.dto.Event
import com.example.event.ui.model.DummyEventData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventListViewModel : ViewModel() {
    private val eventService = AppContainer.getEventService()

    private val _events = MutableStateFlow(DummyEventData.eventList)
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = eventService.getAllEvents()
                result.onSuccess { events ->
                    _events.value = events
                }.onFailure { error ->
                    _errorMessage.value = error.message ?: "Failed to load events"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun refreshEvents() {
        loadEvents()
    }
}

