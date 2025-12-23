package com.example.sana_visprog.repository

import com.example.sana_visprog.model.Province
import com.example.sana_visprog.service.ProvinceService


class ProvinceRepository(private val provinceService: ProvinceService) {
    suspend fun getProvinces(): List<Province> {
        return provinceService.getProvinces().data.map {
            Province(
                id = it.id,
                name = it.name
            )
        }
    }
}