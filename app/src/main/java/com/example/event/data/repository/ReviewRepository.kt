package com.example.event.data.repository

import com.example.event.data.dto.Review
import com.example.event.data.dto.ReviewRequest
import com.example.event.data.service.ApiClient

class ReviewRepository {
    private val apiService = ApiClient.reviewApiService

    suspend fun getAllReviews(): Result<List<Review>> {
        return try {
            val response = apiService.getAllReviews()
            if (response.isSuccessful && response.body()?.success == true) {
                val reviews = response.body()?.data?.map { reviewResponse ->
                    Review(
                        id = reviewResponse.id,
                        userName = reviewResponse.userName,
                        rating = reviewResponse.rating,
                        comment = reviewResponse.comment
                    )
                } ?: emptyList()
                Result.success(reviews)
            } else {
                Result.failure(Exception(response.body()?.message ?: "Failed to load reviews"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewById(id: Int): Result<Review> {
        return try {
            val response = apiService.getReviewById(id)
            if (response.isSuccessful && response.body()?.success == true) {
                val reviewResponse = response.body()?.data
                if (reviewResponse != null) {
                    val review = Review(
                        id = reviewResponse.id,
                        userName = reviewResponse.userName,
                        rating = reviewResponse.rating,
                        comment = reviewResponse.comment,
                    )
                    Result.success(review)
                } else {
                    Result.failure(Exception("Review not found"))
                }
            } else {
                Result.failure(Exception(response.body()?.message ?: "Failed to load review"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviewsByEventId(eventId: Int): Result<List<Review>> {
        return try {
            val response = apiService.getReviewsByEventId(eventId)
            if (response.isSuccessful && response.body()?.success == true) {
                val reviews = response.body()?.data?.map { reviewResponse ->
                    Review(
                        id = reviewResponse.id,
                        userName = reviewResponse.userName,
                        rating = reviewResponse.rating,
                        comment = reviewResponse.comment
                    )
                } ?: emptyList()
                Result.success(reviews)
            } else {
                Result.failure(Exception(response.body()?.message ?: "Failed to load reviews"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createReview(eventId: Int, userName: String, rating: Float, comment: String, photoUrl: String? = null): Result<Review> {
        return try {
            val request = ReviewRequest(
                eventId = eventId,
                userName = userName,
                rating = rating,
                comment = comment,
                photoUrl = photoUrl
            )
            val response = apiService.createReview(request)
            if (response.isSuccessful && response.body()?.success == true) {
                val reviewResponse = response.body()?.data
                if (reviewResponse != null) {
                    val review = Review(
                        id = reviewResponse.id,
                        userName = reviewResponse.userName,
                        rating = reviewResponse.rating,
                        comment = reviewResponse.comment
                    )
                    Result.success(review)
                } else {
                    Result.failure(Exception("Failed to create review"))
                }
            } else {
                Result.failure(Exception(response.body()?.message ?: "Failed to create review"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateReview(id: Int, eventId: Int, userName: String, rating: Float, comment: String, photoUrl: String? = null): Result<Review> {
        return try {
            val request = ReviewRequest(
                eventId = eventId,
                userName = userName,
                rating = rating,
                comment = comment,
                photoUrl = photoUrl
            )
            val response = apiService.updateReview(id, request)
            if (response.isSuccessful && response.body()?.success == true) {
                val reviewResponse = response.body()?.data
                if (reviewResponse != null) {
                    val review = Review(
                        id = reviewResponse.id,
                        userName = reviewResponse.userName,
                        rating = reviewResponse.rating,
                        comment = reviewResponse.comment
                    )
                    Result.success(review)
                } else {
                    Result.failure(Exception("Failed to update review"))
                }
            } else {
                Result.failure(Exception(response.body()?.message ?: "Failed to update review"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteReview(id: Int): Result<String> {
        return try {
            val response = apiService.deleteReview(id)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success("Review deleted successfully")
            } else {
                Result.failure(Exception(response.body()?.message ?: "Failed to delete review"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
