package com.example.event.data.service

import com.example.event.data.dto.Review
import com.example.event.data.repository.ReviewRepository

class ReviewService(private val reviewRepository: ReviewRepository) {

    suspend fun getAllReviews(): Result<List<Review>> {
        return reviewRepository.getAllReviews()
    }

    suspend fun getReviewById(id: Int): Result<Review> {
        if (id <= 0) {
            return Result.failure(Exception("Invalid review ID"))
        }
        return reviewRepository.getReviewById(id)
    }

    suspend fun getReviewsByEventId(eventId: Int): Result<List<Review>> {
        if (eventId <= 0) {
            return Result.failure(Exception("Invalid event ID"))
        }
        return reviewRepository.getReviewsByEventId(eventId)
    }

    suspend fun createReview(eventId: Int, userName: String, rating: Float, comment: String, photoUrl: String? = null): Result<Review> {
        // Validation
        val validationError = validateReviewData(eventId, userName, rating, comment)
        if (validationError != null) {
            return Result.failure(Exception(validationError))
        }

        return reviewRepository.createReview(
            eventId = eventId,
            userName = userName.trim(),
            rating = rating,
            comment = comment.trim(),
            photoUrl = photoUrl?.trim()
        )
    }

    suspend fun updateReview(id: Int, eventId: Int, userName: String, rating: Float, comment: String, photoUrl: String? = null): Result<Review> {
        if (id <= 0) {
            return Result.failure(Exception("Invalid review ID"))
        }

        // Validation
        val validationError = validateReviewData(eventId, userName, rating, comment)
        if (validationError != null) {
            return Result.failure(Exception(validationError))
        }

        return reviewRepository.updateReview(
            id = id,
            eventId = eventId,
            userName = userName.trim(),
            rating = rating,
            comment = comment.trim(),
            photoUrl = photoUrl?.trim()
        )
    }

    suspend fun deleteReview(id: Int): Result<String> {
        if (id <= 0) {
            return Result.failure(Exception("Invalid review ID"))
        }
        return reviewRepository.deleteReview(id)
    }

    private fun validateReviewData(eventId: Int, userName: String, rating: Float, comment: String): String? {
        if (eventId <= 0) {
            return "Invalid event ID"
        }
        if (userName.isBlank()) {
            return "Name cannot be empty"
        }
        if (userName.trim().length < 2) {
            return "Name must be at least 2 characters"
        }
        if (rating < 1f || rating > 5f) {
            return "Rating must be between 1 and 5 stars"
        }
        if (comment.isBlank()) {
            return "Comment cannot be empty"
        }
        if (comment.trim().length < 5) {
            return "Comment must be at least 5 characters"
        }
        return null
    }
}
