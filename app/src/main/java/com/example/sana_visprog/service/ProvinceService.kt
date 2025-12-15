package com.example.sana_visprog.service

import com.example.sana_visprog.model.ApiResponse
import com.example.sana_visprog.model.Province
import retrofit2.http.GET

interface ProvinceService {
    @GET("provinces")
    suspend fun getProvinces(): ApiResponse<List<Province>>
}