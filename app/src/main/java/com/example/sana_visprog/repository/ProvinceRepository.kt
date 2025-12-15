package com.example.sana_visprog.repository

import com.example.sana_visprog.model.ApiResponse
import com.example.sana_visprog.model.Province
import com.example.sana_visprog.service.ProvinceService


class ProvinceRepository(private val provinceService: ProvinceService) {
    suspend fun getProvinces(): ApiResponse<List<Province>> {
        return try {
            provinceService.getProvinces()
        } catch (e: Exception) {
            ApiResponse(error = e.message)
        }
    }
}