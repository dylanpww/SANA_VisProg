package com.example.sana_visprog.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import android.util.Log
import com.example.sana_visprog.SANAVisProgApplication
import com.example.sana_visprog.model.Plan
import com.example.sana_visprog.dto.Plan.PlanItem
import com.example.sana_visprog.dto.Plan.toPlan
import com.example.sana_visprog.model.Destination
import com.example.sana_visprog.repository.DestinationPlanRepository
import com.example.sana_visprog.repository.DestinationRepository
import com.example.sana_visprog.repository.PlanRepository
import com.example.sana_visprog.repository.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface PlanUiState {
    data class Success(val plans: List<PlanItem>) : PlanUiState
    object Idle : PlanUiState
    object Error : PlanUiState
    object Loading : PlanUiState
    object Unauthorized : PlanUiState
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

class PlanViewModel(
    private val planRepository: PlanRepository,
    private val destinationPlanRepository: DestinationPlanRepository,
    private val destinationRepository: DestinationRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    var planUiState: PlanUiState by mutableStateOf(PlanUiState.Loading)
        private set
    var createPlanState: CreatePlanState by mutableStateOf(CreatePlanState.Idle)
        private set

    var updatePlanState: UpdatePlanState by mutableStateOf(UpdatePlanState.Idle)
        private set

    var currentPlanDetail by mutableStateOf<Plan?>(null)
        private set

    var planDetailUiState: PlanUiState by mutableStateOf(PlanUiState.Idle)
        private set

    var allDestinations by mutableStateOf<List<Destination>>(emptyList())
        private set

    var isLoadingDestinations by mutableStateOf(false)
        private set


    fun getPlans() {
        viewModelScope.launch {
            planUiState = PlanUiState.Loading
            try {
                val token = userPreferences.authToken.first()
                if (token.isNullOrBlank()) {
                    planUiState = PlanUiState.Unauthorized
                }else{
                    val finalToken = "Bearer $token"
                    val result = planRepository.getAllPlans(finalToken)
                    planUiState = PlanUiState.Success(result)
                }
            } catch (e: HttpException) {
                if (e.code() == 401 || e.code() == 403) {
                    userPreferences.logout()
                    planUiState = PlanUiState.Unauthorized
                } else {
                    planUiState = PlanUiState.Error
                }
            } catch (e: Exception) {
                e.printStackTrace()
                planUiState = PlanUiState.Error
            }
        }
    }

    fun createPlan(name: String, description: String) {
        viewModelScope.launch {
            createPlanState = CreatePlanState.Loading
            try {
                val token = userPreferences.authToken.first()
                if (token.isNullOrBlank()) {
                    createPlanState = CreatePlanState.Error("Sesi habis, tolong relog")
                    planUiState = PlanUiState.Unauthorized
                }else{
                    val finalToken = "Bearer $token"
                    planRepository.createPlan(finalToken, name, description)
                    createPlanState = CreatePlanState.Success
                }

            } catch (e: Exception) {
                createPlanState = CreatePlanState.Error("Gagal membuat plan: ${e.message}")
            }
        }
    }

    fun updatePlan(planId: Int, name: String, description: String) {
        viewModelScope.launch {
            try {
                val token = userPreferences.authToken.first()
                if (token.isNullOrBlank()) {
                    updatePlanState = UpdatePlanState.Error("Sesi habis, tolong relog")
                    planUiState = PlanUiState.Unauthorized
                }else{
                    val finalToken = "Bearer $token"
                    planRepository.updatePlan(finalToken, planId, name, description)
                    updatePlanState = UpdatePlanState.Success
                }

            } catch (e: Exception) {
                updatePlanState = UpdatePlanState.Error("Gagal update plan: ${e.message}")
            }
        }
    }

    fun deletePlan(planId: Int) {
        viewModelScope.launch {
            try {
                val token = userPreferences.authToken.first()
                if (token.isNullOrBlank()) {
                    planUiState = PlanUiState.Unauthorized
                }else{
                    val finalToken = "Bearer $token"
                    planRepository.deletePlan(finalToken, planId)
                    getPlans()
                }

            } catch (e: Exception) {
            }
        }
    }

    fun isVisited(itemId: Int, currentStatus: Boolean) {
        viewModelScope.launch {
            try {
                val token = userPreferences.authToken.first()
                if (token.isNullOrBlank()) {
                    planUiState = PlanUiState.Unauthorized
                }else{
                    destinationPlanRepository.updateStatus("Bearer $token", itemId, !currentStatus)
                    currentPlanDetail?.let { getPlanDetail(it.id) }
                    getPlans()
                }

            } catch (e: HttpException) {
                if (e.code() == 401) {
                    userPreferences.logout()
                    planUiState = PlanUiState.Unauthorized
                }
            } catch (e: Exception) {
                planUiState = PlanUiState.Error
            }
        }
    }

    fun removeDestinationFromPlan(itemId: Int) {
        viewModelScope.launch {
            try {
                val token = userPreferences.authToken.first() ?: return@launch
                destinationPlanRepository.removeDestination("Bearer $token", itemId)
                getPlans()
            } catch (e: HttpException) {
                if (e.code() == 401) {
                    userPreferences.logout()
                    planUiState = PlanUiState.Unauthorized
                }
            } catch (e: Exception) {
                planUiState = PlanUiState.Error
            }
        }
    }

    fun addDestinationToPlan(planId: Int, destinationId: Int) {
        viewModelScope.launch {
            try {
                val token = userPreferences.authToken.first()
                if (token.isNullOrBlank()) {
                    planUiState = PlanUiState.Unauthorized
                }else{
                    destinationPlanRepository.addDestination("Bearer $token", planId, destinationId)
                    getPlans()
                }

            } catch (e: HttpException) {
                if (e.code() == 401) {
                    userPreferences.logout()
                    planUiState = PlanUiState.Unauthorized
                } else {
                    planUiState = PlanUiState.Error
                }
            } catch (e: Exception) {
                planUiState = PlanUiState.Error
            }
        }
    }

    fun getPlanDetail(planId: Int) {
        viewModelScope.launch {
            planDetailUiState = PlanUiState.Loading
            try {
                val token = userPreferences.authToken.first()
                if (token.isNullOrBlank()) {
                    planUiState = PlanUiState.Unauthorized
                }
                else{
                    val resultDTO = planRepository.getPlan("Bearer $token", planId)
                    currentPlanDetail = resultDTO.toPlan()
                    planDetailUiState = PlanUiState.Success(listOf(resultDTO))
                }




            } catch (e: HttpException) {
                if (e.code() == 401) {
                    userPreferences.logout()
                    planUiState = PlanUiState.Unauthorized
                } else {
                    planDetailUiState = PlanUiState.Error
                }
            } catch (e: Exception) {
                planDetailUiState = PlanUiState.Error
            }
        }
    }

    fun getDestinations() {
        viewModelScope.launch {
            isLoadingDestinations = true
            try {

                val result = destinationRepository.getDestinations()
                allDestinations = result
            } catch (e: Exception) {
            } finally {
                isLoadingDestinations = false
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
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SANAVisProgApplication)
                val planRepository = application.container.planRepository
                val destinationPlanRepository = application.container.destinationPlanRepository
                val destinationRepository = application.container.destinationRepository
                PlanViewModel(
                    planRepository = planRepository,
                    userPreferences = application.container.userPreferences,
                    destinationRepository = destinationRepository,
                    destinationPlanRepository = destinationPlanRepository
                )
            }
        }
    }
}