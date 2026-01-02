package com.example.sana_visprog.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sana_visprog.SANAVisProgApplication
import com.example.sana_visprog.model.Destination
import com.example.sana_visprog.repository.DestinationRepository
import kotlinx.coroutines.launch

class DestinationDetailViewModel(
    private val destinationRepository: DestinationRepository
) : ViewModel() {

    val destination = mutableStateOf<Destination?>(null)
    val isLoading = mutableStateOf(true)
    val errorMessage = mutableStateOf<String?>(null)

    fun getDestinationById(id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val result = destinationRepository.getDestinationById(id)
                destination.value = result
            } catch (e: Exception) {
                errorMessage.value = "Gagal memuat data: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SANAVisProgApplication)
                DestinationDetailViewModel(
                    destinationRepository = application.container.destinationRepository
                )
            }
        }
    }
}