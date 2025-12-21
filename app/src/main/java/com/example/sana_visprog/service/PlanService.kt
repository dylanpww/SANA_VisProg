package com.example.sana_visprog.service

import com.example.sana_visprog.dto.Plan.AddDestinationRequest
import com.example.sana_visprog.dto.Plan.AllPlanResponse
import com.example.sana_visprog.dto.Plan.CreatePlanRequest
import com.example.sana_visprog.dto.Plan.GeneralResponse
import com.example.sana_visprog.dto.Plan.PlanResponse
import com.example.sana_visprog.dto.Plan.UpdateStatusRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PlanService {

    @GET("plans")
    suspend fun getAllPlans(
        @Header("Authorization") token: String
    ): Response<AllPlanResponse>

    @GET("plans/{planId}")
    suspend fun getPlan(
        @Header("Authorization") token: String,
        @Path("planId") planId: Int
    ): Response<PlanResponse>

    @POST("plans")
    suspend fun createPlan(
        @Header("Authorization") token: String,
        @Body request: CreatePlanRequest
    ): Response<PlanResponse>

    @PATCH("plans/{planId}")
    suspend fun updatePlan(
        @Header("Authorization") token: String,
        @Path("planId") planId: Int,
        @Body request: CreatePlanRequest
    ): Response<PlanResponse>

    @DELETE("plans/{planId}")
    suspend fun deletePlan(
        @Header("Authorization") token: String,
        @Path("planId") planId: Int
    ): Response<GeneralResponse>


}