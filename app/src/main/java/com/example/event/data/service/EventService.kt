package com.example.event.data.service

import com.example.event.data.dto.Event
import com.example.event.data.repository.EventRepository

class EventService(private val eventRepository: EventRepository) {

    suspend fun getAllEvents(): Result<List<Event>> {
        return eventRepository.getAllEvents()
    }

    suspend fun getEventById(id: Int): Result<Event> {
        if (id <= 0) {
            return Result.failure(Exception("Invalid event ID"))
        }
        return eventRepository.getEventById(id)
    }

    suspend fun createEvent(name: String, location: String, description: String, photoUrl: String? = null): Result<Event> {
        // Validation
        val validationError = validateEventData(name, location, description)
        if (validationError != null) {
            return Result.failure(Exception(validationError))
        }

        return eventRepository.createEvent(
            name = name.trim(),
            location = location.trim(),
            description = description.trim(),
            photoUrl = photoUrl?.trim()
        )
    }

    suspend fun updateEvent(id: Int, name: String, location: String, description: String, photoUrl: String? = null, rating: Float): Result<Event> {
        if (id <= 0) {
            return Result.failure(Exception("Invalid event ID"))
        }

        // Validation
        val validationError = validateEventData(name, location, description)
        if (validationError != null) {
            return Result.failure(Exception(validationError))
        }

        if (rating < 0 || rating > 5) {
            return Result.failure(Exception("Rating must be between 0 and 5"))
        }

        return eventRepository.updateEvent(
            id = id,
            name = name.trim(),
            location = location.trim(),
            description = description.trim(),
            photoUrl = photoUrl?.trim(),
            rating = rating
        )
    }

    suspend fun deleteEvent(id: Int): Result<String> {
        if (id <= 0) {
            return Result.failure(Exception("Invalid event ID"))
        }
        return eventRepository.deleteEvent(id)
    }

    private fun validateEventData(name: String, location: String, description: String): String? {
        if (name.isBlank()) {
            return "Event name cannot be empty"
        }
        if (name.trim().length < 3) {
            return "Event name must be at least 3 characters"
        }
        if (location.isBlank()) {
            return "Location cannot be empty"
        }
        if (location.trim().length < 3) {
            return "Location must be at least 3 characters"
        }
        if (description.isBlank()) {
            return "Description cannot be empty"
        }
        if (description.trim().length < 10) {
            return "Description must be at least 10 characters"
        }
        return null
    }
}
