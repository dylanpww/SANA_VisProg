package com.example.sana_visprog.repository

import com.example.sana_visprog.dto.Destination.CreateDestinationRequest
import com.example.sana_visprog.dto.Destination.toDestination
import com.example.sana_visprog.model.Destination
import com.example.sana_visprog.service.DestinationService
import okhttp3.MultipartBody

class DestinationRepository(
    private val destinationService: DestinationService
) {

    suspend fun getDestinations(): List<Destination> {
        return try {
            val response = destinationService.getAllDestinations()
            if (response.isSuccessful) {
                response.body()?.data?.map { it.toDestination() } ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getDestinationById(id: Int): Destination? {
        return try {
            val response = destinationService.getDestination(id)
            if (response.isSuccessful) {
                response.body()?.data?.toDestination()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun createDestination(request: CreateDestinationRequest): Boolean {
        return try {
            val response = destinationService.createDestination(request)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun uploadImage(image: MultipartBody.Part): String? {
        return try {
            val response = destinationService.uploadImage(image)
            if (response.isSuccessful) {
                response.body()?.data?.url
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}