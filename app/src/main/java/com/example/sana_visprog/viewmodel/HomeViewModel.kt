package com.example.sana_visprog.viewmodel

import com.example.sana_visprog.SANAVisProgApplication
import com.example.sana_visprog.model.Category
import com.example.sana_visprog.repository.CategoryRepository
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch

class HomeViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val categories = mutableStateOf<List<Category>>(emptyList())
    val categoriesLoading = mutableStateOf(false)
    val categoriesError = mutableStateOf<String?>(null)

    val createCategoryLoading = mutableStateOf(false)
    val createCategoryError = mutableStateOf<String?>(null)
    val createCategorySuccess = mutableStateOf<String?>(null)

    val updateCategoryLoading = mutableStateOf(false)
    val updateCategoryError = mutableStateOf<String?>(null)
    val updateCategorySuccess = mutableStateOf<String?>(null)

    val deleteCategoryLoading = mutableStateOf(false)
    val deleteCategoryError = mutableStateOf<String?>(null)
    val deleteCategorySuccess = mutableStateOf<String?>(null)

    fun getCategories() {
        categoriesLoading.value = true
        categoriesError.value = null

        viewModelScope.launch {
            try {
                val response = categoryRepository.getCategories()
                if (response.data != null) {
                    categories.value = response.data
                } else {
                    categoriesError.value = response.error
                }
            } catch (e: Exception) {
                categoriesError.value = e.message
            } finally {
                categoriesLoading.value = false
            }
        }
    }

    fun createCategory(name: String) {
        createCategoryLoading.value = true
        createCategoryError.value = null
        createCategorySuccess.value = null

        viewModelScope.launch {
            try {
                val response = categoryRepository.createCategory(name)
                if (response.data != null) {
                    createCategorySuccess.value = response.data
                    getCategories()
                } else {
                    createCategoryError.value = response.error
                }
            } catch (e: Exception) {
                createCategoryError.value = e.message
            } finally {
                createCategoryLoading.value = false
            }
        }
    }

    fun updateCategory(categoryId: Int, name: String) {
        updateCategoryLoading.value = true
        updateCategoryError.value = null
        updateCategorySuccess.value = null

        viewModelScope.launch {
            try {
                val response = categoryRepository.updateCategory(categoryId, name)
                if (response.data != null) {
                    updateCategorySuccess.value = response.data
                    getCategories()
                } else {
                    updateCategoryError.value = response.error
                }
            } catch (e: Exception) {
                updateCategoryError.value = e.message
            } finally {
                updateCategoryLoading.value = false
            }
        }
    }

    fun deleteCategory(categoryId: Int) {
        deleteCategoryLoading.value = true
        deleteCategoryError.value = null
        deleteCategorySuccess.value = null

        viewModelScope.launch {
            try {
                val response = categoryRepository.deleteCategory(categoryId)
                if (response.data != null) {
                    deleteCategorySuccess.value = response.data
                    getCategories()
                } else {
                    deleteCategoryError.value = response.error
                }
            } catch (e: Exception) {
                deleteCategoryError.value = e.message
            } finally {
                deleteCategoryLoading.value = false
            }
        }
    }

    fun resetCreateState() {
        createCategoryLoading.value = false
        createCategoryError.value = null
        createCategorySuccess.value = null
    }

    fun resetUpdateState() {
        updateCategoryLoading.value = false
        updateCategoryError.value = null
        updateCategorySuccess.value = null
    }

    fun resetDeleteState() {
        deleteCategoryLoading.value = false
        deleteCategoryError.value = null
        deleteCategorySuccess.value = null
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as SANAVisProgApplication)

                HomeViewModel(
                    categoryRepository = application.container.categoryRepository
                )
            }
        }
    }
}