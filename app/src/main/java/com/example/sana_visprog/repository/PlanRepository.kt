

package com.example.sana_visprog.repository

import com.example.sana_visprog.dto.Plan.*
import com.example.sana_visprog.service.PlanService


class PlanRepository(
    private val planService: PlanService
) {

    suspend fun getAllPlans(token: String): List<PlanItem> {
        val response = planService.getAllPlans(token)
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Gagal mengambil data plan: ${response.message()}")
        }
    }

    suspend fun getPlan(token: String, planId: Int): PlanItem {
        val response = planService.getPlan(token, planId)
        if (response.isSuccessful) {
            return response.body()?.data ?: throw Exception("Data kosong")
        } else {
            throw Exception("Gagal mengambil detail plan")
        }
    }

    suspend fun createPlan(token: String, name: String, description: String?): PlanItem {
        val request = CreatePlanRequest(name, description)
        val response = planService.createPlan(token, request)

        if (response.isSuccessful) {
            return response.body()?.data ?: throw Exception("Respon kosong")
        } else {
            throw Exception("Gagal membuat plan")
        }
    }

    suspend fun updatePlan(token: String, planId: Int, name: String, description: String?): PlanItem {
        val request = CreatePlanRequest(name, description)
        val response = planService.updatePlan(token, planId, request)

        if (response.isSuccessful) {
            return response.body()?.data ?: throw Exception("Respon kosong")
        } else {
            throw Exception("Gagal update plan")
        }
    }

    suspend fun deletePlan(token: String, planId: Int): String {
        val response = planService.deletePlan(token, planId)
        if (response.isSuccessful) {
            return response.body()?.message ?: "Berhasil dihapus"
        } else {
            throw Exception("Gagal menghapus plan")
        }
    }
}