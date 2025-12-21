package com.example.sana_visprog.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sana_visprog.SANAVisProgApplication
import com.example.sana_visprog.dto.Plan.PlanItem
import com.example.sana_visprog.repository.PlanRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed interface PlanUiState {
    data class Success(val plans: List<PlanItem>) : PlanUiState
    object Error : PlanUiState
    object Loading : PlanUiState
}

sealed interface CreatePlanState {
    object Idle : CreatePlanState
    object Loading : CreatePlanState
    object Success : CreatePlanState
    data class Error(val message: String) : CreatePlanState
}

sealed interface UpdatePlanState {
    object Idle : UpdatePlanState
    object Loading : UpdatePlanState
    object Success : UpdatePlanState
    data class Error(val message: String) : UpdatePlanState
}

class PlanViewModel(private val planRepository: PlanRepository) : ViewModel() {

    var planUiState: PlanUiState by mutableStateOf(PlanUiState.Loading)
        private set
    var createPlanState: CreatePlanState by mutableStateOf(CreatePlanState.Idle)
        private set

    var updatePlanState: UpdatePlanState by mutableStateOf(UpdatePlanState.Idle)
        private set

    private val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJkeWxhbiIsImVtYWlsIjoiZHlsYW5AZ21haWwuY29tIiwiaWF0IjoxNzY2MzE0NTY3LCJleHAiOjE3NjYzMTgxNjd9.BER5S5EBOz5Z54LAdr81HDJIUrd2jxZs3j0mqRHK_PQ"

    fun getPlans() {
        viewModelScope.launch {
            planUiState = PlanUiState.Loading
            try {
                val result = planRepository.getAllPlans(token)
                planUiState = PlanUiState.Success(result)
            }catch (e: Exception) {
                planUiState = PlanUiState.Error
            }
        }
    }

    fun createPlan(name: String, description: String) {
        viewModelScope.launch {
            createPlanState = CreatePlanState.Loading
            try {
                planRepository.createPlan(token, name, description)
                delay(500)
                createPlanState = CreatePlanState.Success
            } catch (e: Exception) {
                createPlanState = CreatePlanState.Error("Gagal membuat plan: ${e.message}")
            }
        }
    }

    fun updatePlan(planId: Int, name: String, description: String) {
        viewModelScope.launch {
            try {
                planRepository.updatePlan(token, planId, name, description)
                delay(500)
                updatePlanState = UpdatePlanState.Success
            } catch (e: Exception) {
                updatePlanState = UpdatePlanState.Error("Gagal update plan: ${e.message}")
            }
        }
    }

    fun deletePlan(planId: Int) {
        viewModelScope.launch {
            try {
                planRepository.deletePlan(token, planId)
                getPlans()
            } catch (e: Exception) {
                Log.e("PlanViewModel", "Gagal delete plan: ${e.message}")
            }
        }
    }

    fun resetCreateState() {
        createPlanState = CreatePlanState.Idle
    }

    fun resetUpdateState() {
        updatePlanState = UpdatePlanState.Idle
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SANAVisProgApplication)
                val planRepository = application.container.planRepository
                PlanViewModel(planRepository = planRepository)
            }
        }
    }
}