package com.example.event.data.container

import com.example.event.data.repository.EventRepository
import com.example.event.data.repository.ReviewRepository
import com.example.event.data.service.EventService
import com.example.event.data.service.ReviewService

/**
 * Dependency Injection Container for the application.
 * Provides centralized access to services and repositories.
 */
object AppContainer {
    
    // Repositories
    private val _eventRepository: EventRepository by lazy {
        EventRepository()
    }

    private val _reviewRepository: ReviewRepository by lazy {
        ReviewRepository()
    }

    // Services
    private val _eventService: EventService by lazy {
        EventService(_eventRepository)
    }

    private val _reviewService: ReviewService by lazy {
        ReviewService(_reviewRepository)
    }

    // Public accessors
    fun getEventRepository(): EventRepository = _eventRepository

    fun getReviewRepository(): ReviewRepository = _reviewRepository

    fun getEventService(): EventService = _eventService

    fun getReviewService(): ReviewService = _reviewService
}
