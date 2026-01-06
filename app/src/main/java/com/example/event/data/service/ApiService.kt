package com.example.event.data.service

import com.example.event.data.dto.ApiResponse
import com.example.event.data.dto.EventRequest
import com.example.event.data.dto.EventResponse
import com.example.event.data.dto.ReviewRequest
import com.example.event.data.dto.ReviewResponse
import retrofit2.Response
import retrofit2.http.*

interface EventApiService {
    @GET("events")
    suspend fun getAllEvents(): Response<ApiResponse<List<EventResponse>>>

    @GET("events/{id}")
    suspend fun getEventById(@Path("id") id: Int): Response<ApiResponse<EventResponse>>

    @POST("events")
    suspend fun createEvent(@Body request: EventRequest): Response<ApiResponse<EventResponse>>

    @PUT("events/{id}")
    suspend fun updateEvent(@Path("id") id: Int, @Body request: EventRequest): Response<ApiResponse<EventResponse>>

    @DELETE("events/{id}")
    suspend fun deleteEvent(@Path("id") id: Int): Response<ApiResponse<Any>>
}

interface ReviewApiService {
    @GET("reviews")
    suspend fun getAllReviews(): Response<ApiResponse<List<ReviewResponse>>>

    @GET("reviews/{id}")
    suspend fun getReviewById(@Path("id") id: Int): Response<ApiResponse<ReviewResponse>>

    @GET("reviews/event/{eventId}")
    suspend fun getReviewsByEventId(@Path("eventId") eventId: Int): Response<ApiResponse<List<ReviewResponse>>>

    @POST("reviews")
    suspend fun createReview(@Body request: ReviewRequest): Response<ApiResponse<ReviewResponse>>

    @PUT("reviews/{id}")
    suspend fun updateReview(@Path("id") id: Int, @Body request: ReviewRequest): Response<ApiResponse<ReviewResponse>>

    @DELETE("reviews/{id}")
    suspend fun deleteReview(@Path("id") id: Int): Response<ApiResponse<Any>>
}
