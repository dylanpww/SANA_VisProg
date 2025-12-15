package com.example.sana_visprog.service

import com.example.sana_visprog.dto.Provinces.getAllProvinces
import com.example.sana_visprog.dto.Provinces.getProvinces
import retrofit2.http.GET
import retrofit2.http.Path

interface ProvinceService {
    @GET("provinces")
    suspend fun getProvinces(): getAllProvinces

    @GET("provinces/{provinceId}")
    suspend fun getProvinceById(@Path("provinceId") provinceId: Int): getProvinces
}