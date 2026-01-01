package com.example.sana_visprog.service

import com.example.sana_visprog.dto.Destination.CreateDestinationRequest
import com.example.sana_visprog.dto.Destination.DestinationResponse
import com.example.sana_visprog.model.UploadResponse
import com.example.sana_visprog.model.WebResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface DestinationService {

    @GET("destinations")
    suspend fun getAllDestinations(): Response<WebResponse<List<DestinationResponse>>>

    @GET("destinations/{id}")
    suspend fun getDestination(@Path("id") id: Int): Response<WebResponse<DestinationResponse>>

    @POST("destinations")
    suspend fun createDestination(@Body request: CreateDestinationRequest): Response<WebResponse<DestinationResponse>>

    @Multipart
    @POST("upload-image")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<WebResponse<UploadResponse>>
}