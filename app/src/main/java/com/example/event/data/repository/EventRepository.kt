package com.example.event.data.repository

import com.example.event.data.dto.Event
import com.example.event.data.dto.EventRequest
import com.example.event.data.service.ApiClient

class EventRepository {
    private val apiService = ApiClient.eventApiService

    suspend fun getAllEvents(): Result<List<Event>> {
        return try {
            val response = apiService.getAllEvents()
            if (response.isSuccessful && response.body()?.success == true) {
                val events = response.body()?.data?.map { eventResponse ->
                    Event(
                        id = eventResponse.id,
                        name = eventResponse.name,
                        location = eventResponse.location,
                        photoUrl = eventResponse.photoUrl ?: "",
                        description = eventResponse.description,
                        rating = eventResponse.rating
                    )
                } ?: emptyList()
                Result.success(events)
            } else {
                Result.failure(Exception(response.body()?.message ?: "Failed to load events"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getEventById(id: Int): Result<Event> {
        return try {
            val response = apiService.getEventById(id)
            if (response.isSuccessful && response.body()?.success == true) {
                val eventResponse = response.body()?.data
                if (eventResponse != null) {
                    val event = Event(
                        id = eventResponse.id,
                        name = eventResponse.name,
                        location = eventResponse.location,
                        photoUrl = eventResponse.photoUrl ?: "",
                        description = eventResponse.description,
                        rating = eventResponse.rating
                    )
                    Result.success(event)
                } else {
                    Result.failure(Exception("Event not found"))
                }
            } else {
                Result.failure(Exception(response.body()?.message ?: "Failed to load event"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createEvent(
        name: String,
        location: String,
        description: String,
        photoUrl: String? = null
    ): Result<Event> {
        return try {
            val request = EventRequest(
                name = name,
                location = location,
                description = description,
                photoUrl = photoUrl,
                rating = 0f
            )
            val response = apiService.createEvent(request)
            if (response.isSuccessful && response.body()?.success == true) {
                val eventResponse = response.body()?.data
                if (eventResponse != null) {
                    val event = Event(
                        id = eventResponse.id,
                        name = eventResponse.name,
                        location = eventResponse.location,
                        photoUrl = eventResponse.photoUrl ?: "",
                        description = eventResponse.description,
                        rating = eventResponse.rating
                    )
                    Result.success(event)
                } else {
                    Result.failure(Exception("Failed to create event"))
                }
            } else {
                Result.failure(Exception(response.body()?.message ?: "Failed to create event"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateEvent(id: Int, name: String, location: String, description: String, photoUrl: String? = null, rating: Float): Result<Event> {
        return try {
            val request = EventRequest(
                name = name,
                location = location,
                description = description,
                photoUrl = photoUrl,
                rating = rating
            )
            val response = apiService.updateEvent(id, request)
            if (response.isSuccessful && response.body()?.success == true) {
                val eventResponse = response.body()?.data
                if (eventResponse != null) {
                    val event = Event(
                        id = eventResponse.id,
                        name = eventResponse.name,
                        location = eventResponse.location,
                        photoUrl = eventResponse.photoUrl ?: "",
                        description = eventResponse.description,
                        rating = eventResponse.rating
                    )
                    Result.success(event)
                } else {
                    Result.failure(Exception("Failed to update event"))
                }
            } else {
                Result.failure(Exception(response.body()?.message ?: "Failed to update event"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteEvent(id: Int): Result<String> {
        return try {
            val response = apiService.deleteEvent(id)
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success("Event deleted successfully")
            } else {
                Result.failure(Exception(response.body()?.message ?: "Failed to delete event"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
