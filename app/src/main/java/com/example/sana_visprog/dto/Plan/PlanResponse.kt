package com.example.sana_visprog.dto.Plan

import android.util.Log
import com.example.sana_visprog.dto.Destination.DestinationResponse
import com.example.sana_visprog.dto.Destination.toDestination
import com.example.sana_visprog.dto.DestinationPlan.PlanDestinationResponse
import com.example.sana_visprog.dto.DestinationPlan.toDomain
import com.example.sana_visprog.model.Plan
import com.google.gson.annotations.SerializedName

data class PlanResponse(
    val data: PlanItem
)

data class AllPlanResponse(
    val data: List<PlanItem>
)

data class PlanItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("destinations")
    val destinations: List<PlanDestinationResponse>?
)


data class GeneralResponse(
    val message: String
)

fun PlanItem.toPlan(): Plan {
    Log.d("DEBUG_MAPPER", "Plan: $name | Jml Destinasi JSON: ${destinations?.size ?: 0}")

    // Kalau ada isinya, coba print nama destinasi pertama buat ngecek
    destinations?.forEach {
        Log.d("DEBUG_MAPPER", " - Item JSON: ${it.name}, ID Relasi: ${it.destinationPlanId}")
    }
    return Plan(
        id = id,
        name = name,
        description = description ?: "",
        createdAt = "",
        destinations = destinations?.map { it.toDomain() } ?: emptyList()
    )
}