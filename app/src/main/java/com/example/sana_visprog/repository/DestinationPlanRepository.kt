package com.example.sana_visprog.repository


import com.example.sana_visprog.dto.Plan.AddDestinationRequest
import com.example.sana_visprog.dto.Plan.UpdateStatusRequest
import com.example.sana_visprog.service.DestinationPlanService
import retrofit2.HttpException

class DestinationPlanRepository (
    private val destinationPlanService: DestinationPlanService
){

    suspend fun addDestination(token: String, planId: Int, destinationId: Int): String {
        val request = AddDestinationRequest(destinationId)
        val response = destinationPlanService.addDestination(token, planId, request)

        if (response.isSuccessful) {
            return response.body()?.message ?: "Berhasil ditambahkan"
        } else {
            throw HttpException(response)
        }
    }

    suspend fun removeDestination(token: String, itemId: Int): String {
        val response = destinationPlanService.removeDestination(token, itemId)
        if (response.isSuccessful) {
            return response.body()?.message ?: "Berhasil dihapus"
        } else {
            throw HttpException(response)
        }
    }

    suspend fun updateStatus(token: String, itemId: Int, isVisited: Boolean): String {
        val request = UpdateStatusRequest(isVisited)
        val response = destinationPlanService.updateDestinationStatus(token, itemId, request)

        if (response.isSuccessful) {
            return response.body()?.message ?: "Status berhasil diupdate"
        } else {
            throw HttpException(response)
        }
    }
}