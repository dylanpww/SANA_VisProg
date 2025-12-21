package com.example.sana_visprog.dto.Plan

import com.google.gson.annotations.SerializedName

data class PlanResponse(
    val data: PlanItem
)

data class AllPlanResponse(
    val data: List<PlanItem>
)
data class PlanItem(
    val id: Int,
    val name: String,
    val description: String?,
//    val destinations: List<DestinationPlanItem>?
)

//data class DestinationPlanItem( ntar isi di dto DestinationPlan
//    val id: Int,
//    val isVisited: Boolean,
//    val destination: SimpleDestination?
//)


data class GeneralResponse(
    val message: String
)