package com.example.sana_visprog.service

import com.example.sana_visprog.dto.Plan.AddDestinationRequest
import com.example.sana_visprog.dto.Plan.GeneralResponse
import com.example.sana_visprog.dto.Plan.UpdateStatusRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path


interface DestinationPlanService{
    @POST("plans/{planId}/destinations")
    suspend fun addDestination(
        @Header("Authorization") token: String,
        @Path("planId") planId: Int,
        @Body request: AddDestinationRequest
    ): Response<GeneralResponse>


    @DELETE("plans/items/{id}")
    suspend fun removeDestination(
        @Header("Authorization") token: String,
        @Path("id") itemId: Int
    ): Response<GeneralResponse>

    @PATCH("plans/items/{id}/status")
    suspend fun updateDestinationStatus(
        @Header("Authorization") token: String,
        @Path("id") itemId: Int,
        @Body request: UpdateStatusRequest
    ): Response<GeneralResponse>
}